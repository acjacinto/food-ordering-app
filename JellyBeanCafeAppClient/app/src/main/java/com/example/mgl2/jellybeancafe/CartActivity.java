package com.example.mgl2.jellybeancafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.example.mgl2.jellybeancafe.Adapter.CartAdapter;
import com.example.mgl2.jellybeancafe.Adapter.FavoriteAdapter;
import com.example.mgl2.jellybeancafe.Adapter.NothingSelectedSpinnerAdapter;
import com.example.mgl2.jellybeancafe.Database.ModelDB.Cart;
import com.example.mgl2.jellybeancafe.Database.ModelDB.Favorite;
import com.example.mgl2.jellybeancafe.Model.DataMessage;
import com.example.mgl2.jellybeancafe.Model.MyResponse;
import com.example.mgl2.jellybeancafe.Model.OrderResult;
import com.example.mgl2.jellybeancafe.Model.Token;
import com.example.mgl2.jellybeancafe.Retrofit.IFCMService;
import com.example.mgl2.jellybeancafe.Retrofit.IJBCafeAPI;
import com.example.mgl2.jellybeancafe.Utils.Common;
import com.example.mgl2.jellybeancafe.Utils.RecyclerItemTouchHelper;
import com.example.mgl2.jellybeancafe.Utils.RecyclerItemTouchHelperListener;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener{

    private static final int PAYMENT_REQUEST_CODE = 7777;
    RecyclerView recycler_cart;
    Button btn_place_order;

    TextView txt_total,txt_grand_total;

    List<Cart> cartList = new ArrayList<>();

    CompositeDisposable compositeDisposable;

    CartAdapter cartAdapter;

    RelativeLayout rootLayout;

    IJBCafeAPI mService;
    IJBCafeAPI mServiceScalars;

    //Global String
    String token,amount,orderAddress,orderComment;
    HashMap<String,String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        compositeDisposable = new CompositeDisposable();

        mService = Common.getAPI();
        mServiceScalars = Common.getScalarsAPI();

        recycler_cart = (RecyclerView)findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recycler_cart);

        //For Sum
        txt_total = (TextView)findViewById(R.id.txt_total);
        txt_grand_total = (TextView)findViewById(R.id.txt_grand_total);

        btn_place_order = (Button)findViewById(R.id.btn_place_order);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);

        loadCartItems();

        loadToken();
    }

    private void loadToken() {

        final android.app.AlertDialog waitingDialog = new SpotsDialog(CartActivity.this);
        waitingDialog.show();
        waitingDialog.setMessage("Please wait...");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Common.API_TOKEN_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                waitingDialog.dismiss();
                btn_place_order.setEnabled(false);
                Toast.makeText(CartActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                waitingDialog.dismiss();

                token = responseString;
                btn_place_order.setEnabled(true);

            }
        });
    }

    private void placeOrder() {

        if(Common.currentUser != null) {
            //Create dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Submit Order");

            View submit_order_layout = LayoutInflater.from(this).inflate(R.layout.submit_order_layout, null);

            final EditText edt_comment = (EditText) submit_order_layout.findViewById(R.id.edt_comment);
            final EditText edt_other_address = (EditText) submit_order_layout.findViewById(R.id.edt_other_address);
            final Spinner spinner_barangay = (Spinner) submit_order_layout.findViewById(R.id.spinner_barangay);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_barangay.setPrompt("Select a Barangay");

            spinner_barangay.setAdapter(
                    new NothingSelectedSpinnerAdapter(
                            adapter,
                            R.layout.contact_spinner_row_nothing_selected,
                            // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                            this));
            spinner_barangay.setEnabled(false);

            final RadioButton rdi_user_address = (RadioButton) submit_order_layout.findViewById(R.id.rdi_user_address);
            final RadioButton rdi_other_address = (RadioButton) submit_order_layout.findViewById(R.id.rdi_other_address);

            final RadioButton rdi_credit_card = (RadioButton) submit_order_layout.findViewById(R.id.rdi_credit_card);
            final RadioButton rdi_cod = (RadioButton) submit_order_layout.findViewById(R.id.rdi_cod);

            //Event
            rdi_user_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        edt_other_address.setEnabled(false);
                        spinner_barangay.setEnabled(true);
                }
            });

            rdi_other_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        edt_other_address.setEnabled(true);
                        spinner_barangay.setEnabled(false);
                }
            });

            builder.setView(submit_order_layout);

            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(rdi_credit_card.isChecked()) {

                        orderComment = edt_comment.getText().toString();

                        if (rdi_user_address.isChecked())
                            orderAddress = Common.currentUser.getAddress();
                        else if (rdi_other_address.isChecked())
                            orderAddress = edt_other_address.getText().toString()+" "+ spinner_barangay.getSelectedItem().toString();
                        else
                            orderAddress = "";

                        //Payment
                        DropInRequest dropInRequest = new DropInRequest().clientToken(token);
                        startActivityForResult(dropInRequest.getIntent(CartActivity.this), PAYMENT_REQUEST_CODE);
                    }
                    else if(rdi_cod.isChecked())
                    {
                        orderComment = edt_comment.getText().toString();

                        if (rdi_user_address.isChecked())
                            orderAddress = Common.currentUser.getAddress();
                        else if (rdi_other_address.isChecked())
                            orderAddress = edt_other_address.getText().toString()+" "+ spinner_barangay.getSelectedItem().toString();
                        else
                            orderAddress = "";

                        //QR
                        startActivity(new Intent(CartActivity.this,QRActivity.class));

                        //Submit Order
                        /*compositeDisposable.add(
                                Common.cartRepository.getCartItems()
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(new Consumer<List<Cart>>() {
                                            @Override
                                            public void accept(List<Cart> carts) throws Exception {
                                                if(!TextUtils.isEmpty(orderAddress))
                                                    sendOrderToServer(Common.cartRepository.sumPrice(),
                                                            carts,
                                                            orderComment,orderAddress,
                                                            "COD");
                                                else
                                                    Toast.makeText(CartActivity.this, "Order Address can't be null", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                        );*/
                    }

                }
            });

            builder.show();
        }
        else
        {
           //Require Login
           AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
           builder.setTitle("NOT LOGIN?");
           builder.setMessage("Please login or register account to submit order");
           builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
               }
           }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   startActivity(new Intent(CartActivity.this,MainActivity.class));
                   finish();
               }
           }).show();
        }
    }


    //Ctrl+O


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PAYMENT_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();

                if(Common.cartRepository.sumPrice() > 0)
                {
                    amount = String.valueOf(Common.cartRepository.sumPrice());
                    params = new HashMap<>();


                    params.put("amount",amount);
                    params.put("nonce",strNonce);



                    sendPayment();

                }
                else
                {
                    Toast.makeText(this, "Payment amount is 0", Toast.LENGTH_SHORT).show();
                }
            }
            else if(resultCode == RESULT_CANCELED)
                Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show();
            else
            {
                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("JBCafe_ERROR",error.getMessage());
            }
        }
    }

    private void sendPayment() {

        mServiceScalars.payment(params.get("nonce"),params.get("amount"))
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().toString().contains("Successful"))
                        {
                            Toast.makeText(CartActivity.this, "Transaction successful", Toast.LENGTH_SHORT).show();

                            //Submit Order
                            compositeDisposable.add(
                                    Common.cartRepository.getCartItems()
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(Schedulers.io())
                                            .subscribe(new Consumer<List<Cart>>() {
                                                @Override
                                                public void accept(List<Cart> carts) throws Exception {
                                                    if(!TextUtils.isEmpty(orderAddress))
                                                        sendOrderToServer(Common.cartRepository.sumPrice(),
                                                                carts,
                                                                orderComment,orderAddress,
                                                                "Braintree");
                                                    else
                                                        Toast.makeText(CartActivity.this, "Order Address can't be null", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                            );

                        }
                        else
                        {
                            Toast.makeText(CartActivity.this, "Transaction failed", Toast.LENGTH_SHORT).show();
                        }

                        Log.d("JBCafe_INFO",response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Log.d("JBCafe_INFO",t.getMessage());
                    }
                });

    }

    private void sendOrderToServer(float sumPrice, List<Cart> carts, String orderComment, String orderAddress,String paymentMethod) {
        if(carts.size() > 0)
        {
            String orderDetail = new Gson().toJson(carts);

            mService.submitOrder(sumPrice,orderDetail,orderComment,orderAddress,Common.currentUser.getPhone(),paymentMethod)
                    .enqueue(new Callback<OrderResult>() {
                        @Override
                        public void onResponse(Call<OrderResult> call, Response<OrderResult> response) {
                            sendNotificationToServer(response.body());
                        }

                        @Override
                        public void onFailure(Call<OrderResult> call, Throwable t) {
                            Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void sendNotificationToServer(final OrderResult orderResult) {
        //Get Server Token
        mService.getToken("server_app_01","1")
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        //When we have Token, just send notification to this token
                        Map<String,String> contentSend = new HashMap<>();
                        contentSend.put("title","Jelly Bean Cafe Application");
                        contentSend.put("message","You have new order "+orderResult.getOrderId());
                        DataMessage dataMessage = new DataMessage();
                        if(response.body().getToken() != null)
                            dataMessage.setTo(response.body().getToken());
                        dataMessage.setData(contentSend);

                        IFCMService ifcmService = Common.getFCMService();
                        ifcmService.sendNotification(dataMessage)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if(response.code() == 200)
                                        {
                                            if(response.body().success == 1)
                                            {
                                                Toast.makeText(CartActivity.this, "Thank you, Order placed", Toast.LENGTH_SHORT).show();

                                                //Clear Cart
                                                Common.cartRepository.emptyCart();
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(CartActivity.this, "Send notification failed !", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(CartActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private void loadCartItems() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displayCartItem(carts);

                        //THIS is for CART SUM (TOTAL AND GRAND TOTAL)
                        txt_total.setText(new StringBuilder("₱").append(Common.cartRepository.sumPrice()).append("0"));
                        txt_grand_total.setText(new StringBuilder("₱").append(Common.cartRepository.sumPrice()+30).append("0"));
                    }
                })
        );
    }

    private void displayCartItem(List<Cart> carts) {
        cartList = carts;
        cartAdapter = new CartAdapter(this,carts);
        recycler_cart.setAdapter(cartAdapter);
    }

    //Ctrl + O

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }



    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CartAdapter.CartViewHolder)
        {
            String name = cartList.get(viewHolder.getAdapterPosition()).name;

            final Cart deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            //Delete item from adapter
            cartAdapter.removeItem(deletedIndex);
            //Delete item from Roon database
            Common.cartRepository.deleteCartItem(deletedItem);

            Snackbar snackbar = Snackbar.make(rootLayout,new StringBuilder(name).append(" remove from Favorites List").toString(),
                    Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartAdapter.restoreItem(deletedItem,deletedIndex);
                    Common.cartRepository.insertToCart(deletedItem);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
