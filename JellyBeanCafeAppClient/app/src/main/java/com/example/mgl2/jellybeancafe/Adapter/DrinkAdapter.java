package com.example.mgl2.jellybeancafe.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mgl2.jellybeancafe.Database.ModelDB.Cart;
import com.example.mgl2.jellybeancafe.Database.ModelDB.Favorite;
import com.example.mgl2.jellybeancafe.Interface.ItemClickListener;
import com.example.mgl2.jellybeancafe.Model.Drink;
import com.example.mgl2.jellybeancafe.R;
import com.example.mgl2.jellybeancafe.Retrofit.IJBCafeAPI;
import com.example.mgl2.jellybeancafe.Utils.Common;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkViewHolder>{

    Context context;
    List<Drink> drinkList;


    public DrinkAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_layout,null);
        return new DrinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DrinkViewHolder holder, final int position) {

        holder.txt_price.setText(new StringBuilder("₱").append(drinkList.get(position).Price).toString());
        holder.txt_drink_name.setText(drinkList.get(position).Name);

        final String currentMenuId = drinkList.get(position).MenuId;

        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //CHANGE THIS
                if (currentMenuId.toString().equals("8")) {
                    showAddToCartDialog(position);
                }
                else if(currentMenuId.toString().equals("5")){
                    showAddToCartDialogFrappe(position);
                }
                else if(currentMenuId.toString().equals("6")){
                    showAddToCartDialogFrappe(position);
                }
                else if(currentMenuId.toString().equals("9")){
                    showAddToCartDialogHotDrinks(position);
                }
                else if(currentMenuId.toString().equals("11")){
                    showAddToCartDialogTopping(position);
                }
                else if(currentMenuId.toString().equals("12")){
                    showAddToCartDialogPizza(position);
                }
                else if(currentMenuId.toString().equals("13")){
                    showAddToCartDialogCake(position);
                }
                else if(currentMenuId.toString().equals("14")){
                    showAddToCartDialogTopping(position);
                }
                else if(currentMenuId.toString().equals("15")){
                    showAddToCartDialogTopping(position);
                }
                else {
                    Toast.makeText(context, currentMenuId, Toast.LENGTH_SHORT).show();
                }
            }
        });


        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(holder.img_product);

        /*final String currentMenuId2 = drinkList.get(position).MenuId;*/

        holder.setItemClickListener(new ItemClickListener() { //MENU ITEM DESCRIPTION HERE
            @Override
            public void onClick(View v) {
                if (currentMenuId.toString().equals("5")) {
                    showViewDetailDialog(position);
                } else if (currentMenuId.toString().equals("6")) {
                    showViewDetailDialog(position);
                } else if (currentMenuId.toString().equals("8")) {
                    showViewDetailDialog(position);
                } else if (currentMenuId.toString().equals("9")) {
                    showViewDetailDialog(position);
                } else if (currentMenuId.toString().equals("12")) {
                    showViewDetailDialog(position);
                } else if (currentMenuId.toString().equals("13")) {
                    showViewDetailDialog(position);
                } else if (currentMenuId.toString().equals("11")) {
                    showViewDetailSoloDialog(position);
                } else if (currentMenuId.toString().equals("14")) {
                    showViewDetailSoloDialog(position);
                } else if (currentMenuId.toString().equals("15")) {
                    showViewDetailSoloDialog(position);
                } else {
                    Toast.makeText(context, "No Description", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Favorite System
        if(Common.favoriteRepository.isFavorite(Integer.parseInt(drinkList.get(position).ID)) == 1)
            holder.btn_favorites.setImageResource(R.drawable.ic_favorite_white_24dp);
        else
            holder.btn_favorites.setImageResource(R.drawable.ic_favorite_border_white_24dp);

        holder.btn_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.favoriteRepository.isFavorite(Integer.parseInt(drinkList.get(position).ID)) != 1)
                {
                    addOrRemoveFavorite(drinkList.get(position),true);
                    holder.btn_favorites.setImageResource(R.drawable.ic_favorite_white_24dp);
                }
                else
                {
                    addOrRemoveFavorite(drinkList.get(position),false);
                    holder.btn_favorites.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                }
            }
        });
    }

    //VIEW MENU PRODUCT DETAIL Solo
    private void showViewDetailSoloDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.detail_cart_layout_solo, null);

        //View
        ImageView img_cart_product = (ImageView) itemView.findViewById(R.id.img_cart_product);
        TextView txt_cart_product_name = (TextView) itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_cart_product_desc = (TextView) itemView.findViewById(R.id.txt_cart_product_desc);


        //Set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_cart_product);
        txt_cart_product_name.setText(drinkList.get(position).Name);
        txt_cart_product_desc.setText(drinkList.get(position).Description);

        builder.setView(itemView);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.show();
    }

    //VIEW MENU PRODUCT DETAIL
    private void showViewDetailDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.detail_cart_layout_cold_drink, null);

        //View
        ImageView img_cart_product_size = (ImageView) itemView.findViewById(R.id.img_cart_product_size);
        ImageView img_cart_product = (ImageView) itemView.findViewById(R.id.img_cart_product);
        TextView txt_cart_product_name = (TextView) itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_cart_product_desc = (TextView) itemView.findViewById(R.id.txt_cart_product_desc);


        //Set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_cart_product);
        Picasso.with(context)
                .load(drinkList.get(position).DrinkSizeLink)
                .into(img_cart_product_size);
        txt_cart_product_name.setText(drinkList.get(position).Name);
        txt_cart_product_desc.setText(drinkList.get(position).Description);

        builder.setView(itemView);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builder.show();
    }

    //Add to Cart 'Cake'
    private void showAddToCartDialogCake(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout_cake, null);

        //View
        ImageView img_product_dialog = (ImageView) itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView) itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_price_slice = (TextView) itemView.findViewById(R.id.txt_price1);
        TextView txt_price_whole = (TextView) itemView.findViewById(R.id.txt_price2);

        EditText edt_comment = (EditText) itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_slice = (RadioButton) itemView.findViewById(R.id.rdi_slice);
        RadioButton rdi_whole = (RadioButton) itemView.findViewById(R.id.rdi_whole);


        rdi_slice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sizeOfCup = 0;
            }
        });

        rdi_whole.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sizeOfCup = 1;
            }
        });



        //Set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);
        txt_product_dialog.setText(drinkList.get(position).Name);
        txt_price_slice.setText(new StringBuilder("  ₱ ").append(drinkList.get(position).Price));
        txt_price_whole.setText(new StringBuilder("₱ ").append(drinkList.get(position).Price2));

        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                showConfirmDialogCake(position, txt_count.getNumber());
                dialog.dismiss();
            }
        });

        builder.show();


    }

    //Confirm Dialog for 'Cake'
    private void showConfirmDialogCake(final int position, final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout_pizza,null);


        //view
        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_product);
        final TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_product_price = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        /*TextView txt_size = (TextView)itemView.findViewById(R.id.txt_size);
         */
        //Set data
        Picasso.with(context).load(drinkList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(drinkList.get(position).Name).append(" x")
                .append((Common.sizeOfCup == 0 ? " Slice ":" Whole "))
                .append(number).toString());

        /*txt_sugar.setText(new StringBuilder("Sugar: ").append(Common.sugar).append("%").toString());*/

        //COMPUTATION HERE
        double price = 0;

        if(Common.sizeOfCup == 0) // size Regular
            price = ((Double.parseDouble(drinkList.get(position).Price))*Double.parseDouble(number));

        if(Common.sizeOfCup == 1) // size Large
            price = ((Double.parseDouble(drinkList.get(position).Price2))*Double.parseDouble(number));



        StringBuilder topping_final_comment = new StringBuilder("");
        for(String line:Common.toppingAdded)
            topping_final_comment.append(line).append("\n");


        final double finalPrice = Math.round(price);

        txt_product_price.setText(new StringBuilder("₱").append(finalPrice).append("0"));

        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();

                try {
                    //Add to SQLite
                    //Create new Cart item
                    Cart cartItem = new Cart();
                    cartItem.name = drinkList.get(position).Name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.price = finalPrice;
                    cartItem.size = Common.sizeOfCup;
                    cartItem.link = drinkList.get(position).Link;

                    //Add to DB
                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("JBCafe_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Save item to cart successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setView(itemView);
        builder.show();

    }

    //Add to Cart 'Topping'
    private void showAddToCartDialogTopping(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout_topping, null);

        //View
        ImageView img_product_dialog = (ImageView) itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView) itemView.findViewById(R.id.txt_cart_product_name);

        EditText edt_comment = (EditText) itemView.findViewById(R.id.edt_comment);



        //Set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);
        txt_product_dialog.setText(drinkList.get(position).Name);

        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                showConfirmDialogTopping(position, txt_count.getNumber());
                dialog.dismiss();
            }
        });

        builder.show();


    }

    //Confirm Dialog for 'Topping'
    private void showConfirmDialogTopping(final int position, final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout_pizza,null);


        //view
        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_product);
        final TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_product_price = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        /*TextView txt_size = (TextView)itemView.findViewById(R.id.txt_size);
         */
        //Set data
        Picasso.with(context).load(drinkList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(drinkList.get(position).Name).append(" x")
                .append(number).toString());

        /*txt_sugar.setText(new StringBuilder("Sugar: ").append(Common.sugar).append("%").toString());*/

        //COMPUTATION HERE
        double price = 0;

            price = ((Double.parseDouble(drinkList.get(position).Price))*Double.parseDouble(number));





        StringBuilder topping_final_comment = new StringBuilder("");
        for(String line:Common.toppingAdded)
            topping_final_comment.append(line).append("\n");


        final double finalPrice = Math.round(price);

        txt_product_price.setText(new StringBuilder("₱").append(finalPrice).append("0"));

        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();

                try {
                    //Add to SQLite
                    //Create new Cart item
                    Cart cartItem = new Cart();
                    cartItem.name = drinkList.get(position).Name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.price = finalPrice;
                    cartItem.link = drinkList.get(position).Link;

                    //Add to DB
                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("JBCafe_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Save item to cart successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setView(itemView);
        builder.show();

    }

    //Add to Cart 'PIZZA'
    private void showAddToCartDialogPizza(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout_pizza, null);

        //View
        ImageView img_product_dialog = (ImageView) itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView) itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_price_slice = (TextView) itemView.findViewById(R.id.txt_price1);
        TextView txt_price_whole = (TextView) itemView.findViewById(R.id.txt_price2);

        EditText edt_comment = (EditText) itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_slice = (RadioButton) itemView.findViewById(R.id.rdi_slice);
        RadioButton rdi_whole = (RadioButton) itemView.findViewById(R.id.rdi_whole);


        rdi_slice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sizeOfCup = 0;
            }
        });

        rdi_whole.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sizeOfCup = 1;
            }
        });



        //Set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);
        txt_product_dialog.setText(drinkList.get(position).Name);
        txt_price_slice.setText(new StringBuilder("  ₱ ").append(drinkList.get(position).Price));
        txt_price_whole.setText(new StringBuilder("₱ ").append(drinkList.get(position).Price2));

        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                showConfirmDialogPizza(position, txt_count.getNumber());
                dialog.dismiss();
            }
        });

        builder.show();


    }

    //Confirm Dialog for 'PIZZA'
    private void showConfirmDialogPizza(final int position, final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout_pizza,null);


        //view
        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_product);
        final TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_product_price = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        /*TextView txt_size = (TextView)itemView.findViewById(R.id.txt_size);
*/
        //Set data
        Picasso.with(context).load(drinkList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(drinkList.get(position).Name).append(" x")
                .append((Common.sizeOfCup == 0 ? " Slice(1/4) ":" Whole "))
                .append(number).toString());

        /*txt_sugar.setText(new StringBuilder("Sugar: ").append(Common.sugar).append("%").toString());*/

        //COMPUTATION HERE
        double price = 0;

        if(Common.sizeOfCup == 0) // size Regular
            price = ((Double.parseDouble(drinkList.get(position).Price))*Double.parseDouble(number));

        if(Common.sizeOfCup == 1) // size Large
            price = ((Double.parseDouble(drinkList.get(position).Price2))*Double.parseDouble(number));



        StringBuilder topping_final_comment = new StringBuilder("");
        for(String line:Common.toppingAdded)
            topping_final_comment.append(line).append("\n");


        final double finalPrice = Math.round(price);

        txt_product_price.setText(new StringBuilder("₱").append(finalPrice).append("0"));

        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();

                try {
                    //Add to SQLite
                    //Create new Cart item
                    Cart cartItem = new Cart();
                    cartItem.name = drinkList.get(position).Name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.price = finalPrice;
                    cartItem.size = Common.sizeOfCup;
                    cartItem.link = drinkList.get(position).Link;

                    //Add to DB
                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("JBCafe_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Save item to cart successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setView(itemView);
        builder.show();

    }

    //Add to Cart 'HOT DRINK'
    private void showAddToCartDialogHotDrinks(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout_hot_drinks, null);

        //View
        ImageView img_product_dialog = (ImageView) itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView) itemView.findViewById(R.id.txt_cart_product_name);

        EditText edt_comment = (EditText) itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_sizeS = (RadioButton) itemView.findViewById(R.id.rdi_sizeS);
        RadioButton rdi_sizeM = (RadioButton) itemView.findViewById(R.id.rdi_sizeM);
        /*RadioButton rdi_sizeL = (RadioButton)itemView.findViewById(R.id.rdi_sizeL);*/

        rdi_sizeS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sizeOfCup = 0;
            }
        });

        rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sizeOfCup = 1;
            }
        });

        /*rdi_sizeL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    Common.sizeOfCup=2;
            }
        });*/

        RadioButton rdi_sugar_100 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_100);
        RadioButton rdi_sugar_75 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_75);
        RadioButton rdi_sugar_50 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_50);
        RadioButton rdi_sugar_25 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_25);
        RadioButton rdi_sugar_free = (RadioButton) itemView.findViewById(R.id.rdi_sugar_free);

        rdi_sugar_25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 25;
            }
        });

        rdi_sugar_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 50;
            }
        });

        rdi_sugar_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 75;
            }
        });

        rdi_sugar_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 100;
            }
        });

        rdi_sugar_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 0;
            }
        });

        /*RadioButton rdi_ice_100 = (RadioButton)itemView.findViewById(R.id.rdi_ice_100);
        RadioButton rdi_ice_75 = (RadioButton)itemView.findViewById(R.id.rdi_ice_75);
        RadioButton rdi_ice_50 = (RadioButton)itemView.findViewById(R.id.rdi_ice_50);
        RadioButton rdi_ice_25 = (RadioButton)itemView.findViewById(R.id.rdi_ice_25);
        RadioButton rdi_ice_free = (RadioButton)itemView.findViewById(R.id.rdi_ice_free);*/

        /*RecyclerView recycler_topping = (RecyclerView) itemView.findViewById(R.id.recycler_topping);
        recycler_topping.setLayoutManager(new LinearLayoutManager(context));
        recycler_topping.setHasFixedSize(true);

        MultiChoiceAdapter adapter = new MultiChoiceAdapter(context, Common.toppingList);
        recycler_topping.setAdapter(adapter);*/

        //Set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);
        txt_product_dialog.setText(drinkList.get(position).Name);

        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*if(Common.sizeOfCup == -1)
                {
                    Toast.makeText(context, "Please choose size of cup", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Common.sugar == -1)
                {
                    Toast.makeText(context, "Please choose sugar level", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                showConfirmDialogFrappe(position, txt_count.getNumber());
                dialog.dismiss();
            }
        });

        builder.show();

    }

    //Add to Cart 'FRAPPE'
    private void showAddToCartDialogFrappe(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.add_to_cart_layout_frappe, null);

        //View
        ImageView img_product_dialog = (ImageView) itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
        TextView txt_product_dialog = (TextView) itemView.findViewById(R.id.txt_cart_product_name);

        EditText edt_comment = (EditText) itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_sizeS = (RadioButton) itemView.findViewById(R.id.rdi_sizeS);
        RadioButton rdi_sizeM = (RadioButton) itemView.findViewById(R.id.rdi_sizeM);
        /*RadioButton rdi_sizeL = (RadioButton)itemView.findViewById(R.id.rdi_sizeL);*/

        rdi_sizeS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sizeOfCup = 0;
            }
        });

        rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sizeOfCup = 1;
            }
        });


        RadioButton rdi_sugar_100 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_100);
        RadioButton rdi_sugar_75 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_75);
        RadioButton rdi_sugar_50 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_50);
        RadioButton rdi_sugar_25 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_25);
        RadioButton rdi_sugar_free = (RadioButton) itemView.findViewById(R.id.rdi_sugar_free);

        rdi_sugar_25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 25;
            }
        });

        rdi_sugar_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 50;
            }
        });

        rdi_sugar_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 75;
            }
        });

        rdi_sugar_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 100;
            }
        });

        rdi_sugar_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Common.sugar = 0;
            }
        });

        /*RadioButton rdi_ice_100 = (RadioButton)itemView.findViewById(R.id.rdi_ice_100);
        RadioButton rdi_ice_75 = (RadioButton)itemView.findViewById(R.id.rdi_ice_75);
        RadioButton rdi_ice_50 = (RadioButton)itemView.findViewById(R.id.rdi_ice_50);
        RadioButton rdi_ice_25 = (RadioButton)itemView.findViewById(R.id.rdi_ice_25);
        RadioButton rdi_ice_free = (RadioButton)itemView.findViewById(R.id.rdi_ice_free);*/

        /*RecyclerView recycler_topping = (RecyclerView) itemView.findViewById(R.id.recycler_topping);
        recycler_topping.setLayoutManager(new LinearLayoutManager(context));
        recycler_topping.setHasFixedSize(true);

        MultiChoiceAdapter adapter = new MultiChoiceAdapter(context, Common.toppingList);
        recycler_topping.setAdapter(adapter);*/

        //Set data
        Picasso.with(context)
                .load(drinkList.get(position).Link)
                .into(img_product_dialog);
        txt_product_dialog.setText(drinkList.get(position).Name);

        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*if(Common.sizeOfCup == -1)
                {
                    Toast.makeText(context, "Please choose size of cup", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Common.sugar == -1)
                {
                    Toast.makeText(context, "Please choose sugar level", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                showConfirmDialogFrappe(position, txt_count.getNumber());
                dialog.dismiss();
            }
        });

        builder.show();

    }

    //Confirm Dialog for 'FRAPPE'
    private void showConfirmDialogFrappe(final int position, final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout_frappe,null);


        //view
        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_product);
        final TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_product_price = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        TextView txt_sugar = (TextView)itemView.findViewById(R.id.txt_sugar);

        //Set data
        Picasso.with(context).load(drinkList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(drinkList.get(position).Name).append(" x")
                .append((Common.sizeOfCup == 0 ? " Regular ":" Large "))
                .append(number).toString());
        /*.append((Common.sizeOfCup == 0 ? " Small":" Medium").toString()));*/ //THIS

        txt_sugar.setText(new StringBuilder("Sugar: ").append(Common.sugar).append("%").toString());

        //COMPUTATION HERE
        double price = 0;

        if(Common.sizeOfCup == 0) // size Regular
            price = ((Double.parseDouble(drinkList.get(position).Price)+ 0.00)*Double.parseDouble(number));

        if(Common.sizeOfCup == 1) // size Large
            price = ((Double.parseDouble(drinkList.get(position).Price)+ 15)*Double.parseDouble(number));



        StringBuilder topping_final_comment = new StringBuilder("");
        for(String line:Common.toppingAdded)
            topping_final_comment.append(line).append("\n");


        final double finalPrice = Math.round(price);

        txt_product_price.setText(new StringBuilder("₱").append(finalPrice).append("0"));

        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();

                try {
                    //Add to SQLite
                    //Create new Cart item
                    Cart cartItem = new Cart();
                    cartItem.name = drinkList.get(position).Name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.sugar = Common.sugar;
                    cartItem.price = finalPrice;
                    cartItem.size = Common.sizeOfCup;
                    cartItem.link = drinkList.get(position).Link;

                    //Add to DB
                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("JBCafe_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Save item to cart successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setView(itemView);
        builder.show();

    }


    private void addOrRemoveFavorite(Drink drink, boolean isAdd) {
        Favorite favorite = new Favorite();
        favorite.id = drink.ID;
        favorite.link = drink.Link;
        favorite.name = drink.Name;
        favorite.price = drink.Price;
        favorite.menuId = drink.MenuId;

        if(isAdd)
            Common.favoriteRepository.insertFav(favorite);
        else
            Common.favoriteRepository.delete(favorite);
    }


    private void showAddToCartDialog(final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.add_to_cart_layout, null);

            //View
            ImageView img_product_dialog = (ImageView) itemView.findViewById(R.id.img_cart_product);
            final ElegantNumberButton txt_count = (ElegantNumberButton) itemView.findViewById(R.id.txt_count);
            TextView txt_product_dialog = (TextView) itemView.findViewById(R.id.txt_cart_product_name);

            EditText edt_comment = (EditText) itemView.findViewById(R.id.edt_comment);

            RadioButton rdi_sizeS = (RadioButton) itemView.findViewById(R.id.rdi_sizeS);
            RadioButton rdi_sizeM = (RadioButton) itemView.findViewById(R.id.rdi_sizeM);
            /*RadioButton rdi_sizeL = (RadioButton)itemView.findViewById(R.id.rdi_sizeL);*/

            rdi_sizeS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Common.sizeOfCup = 0;
                }
            });

            rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Common.sizeOfCup = 1;
                }
            });

        /*rdi_sizeL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    Common.sizeOfCup=2;
            }
        });*/

            RadioButton rdi_sugar_100 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_100);
            RadioButton rdi_sugar_75 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_75);
            RadioButton rdi_sugar_50 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_50);
            RadioButton rdi_sugar_25 = (RadioButton) itemView.findViewById(R.id.rdi_sugar_25);
            RadioButton rdi_sugar_free = (RadioButton) itemView.findViewById(R.id.rdi_sugar_free);

            rdi_sugar_25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Common.sugar = 25;
                }
            });

            rdi_sugar_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Common.sugar = 50;
                }
            });

            rdi_sugar_75.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Common.sugar = 75;
                }
            });

            rdi_sugar_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Common.sugar = 100;
                }
            });

            rdi_sugar_free.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Common.sugar = 0;
                }
            });

        /*RadioButton rdi_ice_100 = (RadioButton)itemView.findViewById(R.id.rdi_ice_100);
        RadioButton rdi_ice_75 = (RadioButton)itemView.findViewById(R.id.rdi_ice_75);
        RadioButton rdi_ice_50 = (RadioButton)itemView.findViewById(R.id.rdi_ice_50);
        RadioButton rdi_ice_25 = (RadioButton)itemView.findViewById(R.id.rdi_ice_25);
        RadioButton rdi_ice_free = (RadioButton)itemView.findViewById(R.id.rdi_ice_free);*/

            RecyclerView recycler_topping = (RecyclerView) itemView.findViewById(R.id.recycler_topping);
            recycler_topping.setLayoutManager(new LinearLayoutManager(context));
            recycler_topping.setHasFixedSize(true);

            MultiChoiceAdapter adapter = new MultiChoiceAdapter(context, Common.toppingList);
            recycler_topping.setAdapter(adapter);

            //Set data
            Picasso.with(context)
                    .load(drinkList.get(position).Link)
                    .into(img_product_dialog);
            txt_product_dialog.setText(drinkList.get(position).Name);

            builder.setView(itemView);
            builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                /*if(Common.sizeOfCup == -1)
                {
                    Toast.makeText(context, "Please choose size of cup", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Common.sugar == -1)
                {
                    Toast.makeText(context, "Please choose sugar level", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                    showConfirmDialog(position, txt_count.getNumber());
                    dialog.dismiss();
                }
            });

            builder.show();
    }

    private void showConfirmDialog(final int position, final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout,null);


        //view
        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_product);
        final TextView txt_product_dialog = (TextView)itemView.findViewById(R.id.txt_cart_product_name);
        TextView txt_product_price = (TextView)itemView.findViewById(R.id.txt_cart_product_price);
        TextView txt_sugar = (TextView)itemView.findViewById(R.id.txt_sugar);
        final TextView txt_topping_extra = (TextView)itemView.findViewById(R.id.txt_topping_extra);

        //Set data
        Picasso.with(context).load(drinkList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(drinkList.get(position).Name).append(" x")
        .append((Common.sizeOfCup == 0 ? " Regular ":" Large "))
        .append(number).toString());
        /*.append((Common.sizeOfCup == 0 ? " Small":" Medium").toString()));*/ //THIS

        txt_sugar.setText(new StringBuilder("Sugar: ").append(Common.sugar).append("%").toString());

        //COMPUTATION HERE
        double price = 0;

        if(Common.sizeOfCup == 0) // size Regular
            price = ((Double.parseDouble(drinkList.get(position).Price)+ 0.00)*Double.parseDouble(number)) + Common.toppingPrice;

        if(Common.sizeOfCup == 1) // size Large
            price = ((Double.parseDouble(drinkList.get(position).Price)+ 15)*Double.parseDouble(number)) + Common.toppingPrice;

        /*if(Common.sizeOfCup == 2) // size L
            price = ((Double.parseDouble(drinkList.get(position).Price)+ 20)*Double.parseDouble(number)) + Common.toppingPrice;
*/
        /*double price = (Double.parseDouble(drinkList.get(position).Price)*Double.parseDouble(number)) + Common.toppingPrice;*/

        /*if(Common.sizeOfCup == 1) // size M
            price+=10;

        if(Common.sizeOfCup == 2) // size L
            price+=20;*/




        StringBuilder topping_final_comment = new StringBuilder("");
        for(String line:Common.toppingAdded)
            topping_final_comment.append(line).append("\n");

        txt_topping_extra.setText(topping_final_comment);

        final double finalPrice = Math.round(price);

        txt_product_price.setText(new StringBuilder("₱").append(finalPrice).append("0"));

        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();

                try {
                    //Add to SQLite
                    //Create new Cart item
                    Cart cartItem = new Cart();
                    cartItem.name = drinkList.get(position).Name;
                    cartItem.amount = Integer.parseInt(number);
                    cartItem.sugar = Common.sugar;
                    cartItem.price = finalPrice;
                    cartItem.size = Common.sizeOfCup;
                    cartItem.toppingExtras = txt_topping_extra.getText().toString();
                    cartItem.link = drinkList.get(position).Link;

                    //Add to DB
                    Common.cartRepository.insertToCart(cartItem);

                    Log.d("JBCafe_DEBUG", new Gson().toJson(cartItem));

                    Toast.makeText(context, "Save item to cart successfully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setView(itemView);
        builder.show();

    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
