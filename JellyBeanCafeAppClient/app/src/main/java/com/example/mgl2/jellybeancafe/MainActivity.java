package com.example.mgl2.jellybeancafe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mgl2.jellybeancafe.Adapter.NothingSelectedSpinnerAdapter;
import com.example.mgl2.jellybeancafe.Model.CheckUserResponse;
import com.example.mgl2.jellybeancafe.Model.User;
import com.example.mgl2.jellybeancafe.Retrofit.IJBCafeAPI;
import com.example.mgl2.jellybeancafe.Utils.Common;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSION = 1001;
    Button btn_continue;
    Button btn_signin;

    IJBCafeAPI mService;


    //Ctrl + O


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case REQUEST_PERMISSION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
                break;
                default:
                    break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_PERMISSION);

        mService = Common.getAPI();


        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inflate Login
                showloginPage();
            }
        });

        btn_continue = (Button)findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLoginPage(LoginType.PHONE);
            }
        });


        //Check session
        if(AccountKit.getCurrentAccessToken() != null)
        {
            final android.app.AlertDialog alertDialog = new SpotsDialog(MainActivity.this);
            alertDialog.show();
            alertDialog.setMessage("Please waiting...");

            //Auto login
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {

                    mService.checkExistsUser(account.getPhoneNumber().toString())
                            .enqueue(new Callback<CheckUserResponse>() {
                                @Override
                                public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                    CheckUserResponse userResponse = response.body();
                                    if(userResponse.isExists())
                                    {
                                        //Fetch information

                                        mService.getUserInformation(account.getPhoneNumber().toString())
                                                .enqueue(new Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {
                                                        //If User already exist, just start new Activity
                                                        alertDialog.dismiss();

                                                        Common.currentUser = response.body(); // Fix here

                                                        //Update Token
                                                        updateTokenToServer();
                                                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                                        finish(); //Close MainActivity
                                                    }

                                                    @Override
                                                    public void onFailure(Call<User> call, Throwable t) {
                                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                    }
                                    else
                                    {
                                        //Else , need register
                                        alertDialog.dismiss();

                                        showRegisterDialog(account.getPhoneNumber().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckUserResponse> call, Throwable t) {

                                }
                            });
                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Log.d("ERROR",accountKitError.getErrorType().getMessage());
                }
            });
        }

    }

    //SIGN IN
    private void showloginPage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        LayoutInflater inflater = this.getLayoutInflater();
        View login_layout = inflater.inflate(R.layout.login_layout,null);

        final MaterialEditText edt_email = (MaterialEditText)login_layout.findViewById(R.id.edt_email);
        final TextInputEditText edt_password = (TextInputEditText)login_layout.findViewById(R.id.edt_password);

        Button btn_login = (Button)login_layout.findViewById(R.id.btn_login);

        builder.setView(login_layout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();

                if (TextUtils.isEmpty(edt_email.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_password.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                /*final android.app.AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                waitingDialog.setMessage("Please waiting...");*/


            }
        });

    }




    private void startLoginPage(LoginType loginType) {
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,builder.build());
        startActivityForResult(intent,REQUEST_CODE);
    }

    //Ctrl + O


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE)
        {
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if(result.getError() != null)
            {
                Toast.makeText(this, ""+result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            }
            else if(result.wasCancelled())
            {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(result.getAccessToken() != null)
                {
                    final android.app.AlertDialog alertDialog = new SpotsDialog(MainActivity.this);
                    alertDialog.show();
                    alertDialog.setMessage("Please waiting...");

                    //Get User phone and Check exist on server
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {

                            mService.checkExistsUser(account.getPhoneNumber().toString())
                                    .enqueue(new Callback<CheckUserResponse>() {
                                        @Override
                                        public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                            CheckUserResponse userResponse = response.body();
                                            if(userResponse.isExists())
                                            {
                                                //Fetch information

                                                mService.getUserInformation(account.getPhoneNumber().toString())
                                                        .enqueue(new Callback<User>() {
                                                            @Override
                                                            public void onResponse(Call<User> call, Response<User> response) {
                                                                //If User already exist, just start new Activity
                                                                alertDialog.dismiss();

                                                                //Fixed first time crash
                                                                Common.currentUser = response.body();

                                                                updateTokenToServer(); //Update

                                                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                                                finish(); //Close MainActivity
                                                            }

                                                            @Override
                                                            public void onFailure(Call<User> call, Throwable t) {
                                                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });


                                            }
                                            else
                                            {
                                                //Else , need register
                                                alertDialog.dismiss();

                                                showRegisterDialog(account.getPhoneNumber().toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CheckUserResponse> call, Throwable t) {

                                        }
                                    });
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                                Log.d("ERROR",accountKitError.getErrorType().getMessage());
                        }
                    });
                }
            }
        }
    }

    private void showRegisterDialog(final String phone) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("REGISTER");


        LayoutInflater inflater = this.getLayoutInflater();
        View register_layout = inflater.inflate(R.layout.register_layout,null);

        final MaterialEditText edt_name = (MaterialEditText)register_layout.findViewById(R.id.edt_name);
        final MaterialEditText edt_address = (MaterialEditText)register_layout.findViewById(R.id.edt_address);
        final MaterialEditText edt_email = (MaterialEditText)register_layout.findViewById(R.id.edt_email);
        final TextInputEditText edt_password = (TextInputEditText)register_layout.findViewById(R.id.edt_password);

        final Spinner spinner_barangay = (Spinner) register_layout.findViewById(R.id.spinner_barangay);
        //Birthday Spinner
        final Spinner spinner_year = (Spinner) register_layout.findViewById(R.id.spinner_year);
        final Spinner spinner_month = (Spinner) register_layout.findViewById(R.id.spinner_month);
        final Spinner spinner_day = (Spinner) register_layout.findViewById(R.id.spinner_day);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_barangay.setPrompt("Select a Barangay");

        spinner_barangay.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        //Year
        ArrayAdapter<CharSequence> adapter_year = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setPrompt("Select a Year");

        spinner_year.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter_year,
                        R.layout.contact_spinner_row_nothing_selected_year,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        //Month
        ArrayAdapter<CharSequence> adapter_month = ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setPrompt("Select a Month");

        spinner_month.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter_month,
                        R.layout.contact_spinner_row_nothing_selected_month,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        //Day
        ArrayAdapter<CharSequence> adapter_day = ArrayAdapter.createFromResource(this, R.array.day_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setPrompt("Select a Month");

        spinner_day.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter_day,
                        R.layout.contact_spinner_row_nothing_selected_day,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));


        final Button btn_register = (Button)register_layout.findViewById(R.id.btn_register);

        CheckBox ckb_agree = (CheckBox)register_layout.findViewById(R.id.ckb_agree);

        TextView txt_terms_and_conditions = (TextView)register_layout.findViewById(R.id.txt_terms_and_conditions);

        /*edt_birthdate.addTextChangedListener(new PatternedTextWatcher("####-##-##"));*/

        builder.setView(register_layout);
        final AlertDialog dialog = builder.create();

        txt_terms_and_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTermsAndConditionsDialog();
            }
        });


        ckb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if ( isChecked )
                {
                    btn_register.setEnabled(true);

                    //Event
                    btn_register.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();


                            if (TextUtils.isEmpty(edt_name.getText().toString())) {
                                Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(edt_address.getText().toString())) {
                                Toast.makeText(MainActivity.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(edt_email.getText().toString())) {
                                Toast.makeText(MainActivity.this, "Please enter your Email Address", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (TextUtils.isEmpty(edt_password.getText().toString())) {
                                Toast.makeText(MainActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (edt_password.length() < 6) {
                                Toast.makeText(MainActivity.this, "you need to fill your password with a minimum of 6 characters.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //Terms and Condition Show
                            /*showTermsAndConditionsDialog();*/

                            final android.app.AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                            waitingDialog.show();
                            waitingDialog.setMessage("Please waiting...");

                            mService.registerNewUser(phone,
                                    edt_name.getText().toString(),
                                    spinner_year.getSelectedItem().toString()+"-"+spinner_month.getSelectedItem().toString()+"-"+spinner_day.getSelectedItem().toString(),
                                    edt_email.getText().toString(),
                                    edt_password.getText().toString(),
                                    edt_address.getText().toString(),
                                    spinner_barangay.getSelectedItem().toString())
                                    .enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {

                                            waitingDialog.dismiss();
                                            User user = response.body();
                                            if (TextUtils.isEmpty(user.getError_msg())) {
                                                Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                Common.currentUser = response.body();
                                                //Update Token
                                                updateTokenToServer();
                                                //Start new activity
                                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                                finish();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {

                                            waitingDialog.dismiss();

                                        }
                                    });

                        }

                    });




                }else{
                    btn_register.setEnabled(false);
                }
            }
        });

        dialog.show();
    }


    //Terms and Conditions
    private void showTermsAndConditionsDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        LayoutInflater inflater = this.getLayoutInflater();
        View terms_and_conditions_layout = inflater.inflate(R.layout.terms_and_conditions_layout,null);

        Button btn_ok = (Button)terms_and_conditions_layout.findViewById(R.id.btn_ok);

        builder.setView(terms_and_conditions_layout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.mgl2.jellybeancafe",
                    PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    //Exit Application when click BACK button
    boolean isBackButtonClicked = false;
    //Ctrl+O

    @Override
    public void onBackPressed() {
        if(isBackButtonClicked){
            super.onBackPressed();
            return;
        }
        this.isBackButtonClicked = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        isBackButtonClicked = false;
        super.onResume();
    }

    private void updateTokenToServer() {
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        IJBCafeAPI mService = Common.getAPI();
                        mService.updateToken(Common.currentUser.getPhone(),
                                instanceIdResult.getToken()
                                ,"0")
                                .enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Log.d("DEBUG",response.body());
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Log.d("DEBUG",t.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
