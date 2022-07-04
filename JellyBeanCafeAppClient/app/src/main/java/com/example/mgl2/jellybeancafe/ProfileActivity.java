package com.example.mgl2.jellybeancafe;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mgl2.jellybeancafe.Model.CheckUserResponse;
import com.example.mgl2.jellybeancafe.Model.User;
import com.example.mgl2.jellybeancafe.Retrofit.IJBCafeAPI;
import com.example.mgl2.jellybeancafe.Utils.Common;
import com.example.mgl2.jellybeancafe.Utils.ProgressRequestBody;
import com.example.mgl2.jellybeancafe.Utils.UploadCallBack;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1222;
    CircleImageView user_profile_photo;
    TextView user_profile_name,user_phone;
    ImageView img_qr_code_scanner,img_user_settings;

    Uri selectedFileUri;

    IJBCafeAPI mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mService = Common.getAPI();

        user_profile_name = (TextView)findViewById(R.id.user_profile_name);
        user_phone = (TextView)findViewById(R.id.user_phone);
        user_profile_photo = (CircleImageView) findViewById(R.id.user_profile_photo);

        img_qr_code_scanner = (ImageView)findViewById(R.id.img_qr_code_scanner);
        img_user_settings = (ImageView)findViewById(R.id.img_user_settings);

        final Activity activity = this;

        img_qr_code_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


        /*user_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.currentUser != null)
                    chooseImage();
            }
        });*/
        checkSessionLogin();
    }

    private void checkSessionLogin() {
        if(AccountKit.getCurrentAccessToken() != null)
        {
            /*swipeRefreshLayout.setRefreshing(true);*/

            //Check exists user on Server (MySQL)
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
                                        //Request Information of current User
                                        mService.getUserInformation(account.getPhoneNumber().toString())
                                                .enqueue(new Callback<User>() {
                                                    @Override
                                                    public void onResponse(Call<User> call, Response<User> response) {
                                                        Common.currentUser = response.body();
                                                        if(Common.currentUser != null) {
                                                            /*swipeRefreshLayout.setRefreshing(false);*/


                                                            //Set info
                                                            user_profile_name.setText(Common.currentUser.getName());
                                                            user_phone.setText(Common.currentUser.getPhone());

                                                            //Set avatar
                                                            if (!TextUtils.isEmpty(Common.currentUser.getAvatarUrl())) {
                                                                Picasso.with(getBaseContext())
                                                                        .load(new StringBuilder(Common.BASE_URL)
                                                                                .append("user_avatar/")
                                                                                .append(Common.currentUser.getAvatarUrl()).toString())
                                                                        .into(user_profile_photo);
                                                            }

                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<User> call, Throwable t) {
                                                        /*swipeRefreshLayout.setRefreshing(false);*/
                                                        Log.d("ERROR",t.getMessage());
                                                    }
                                                });
                                    }
                                    else
                                    {
                                        //If user does not exists on Database, just make login
                                        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                                    Log.d("ERROR",t.getMessage());
                                }
                            });
                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Log.d("ERROR",accountKitError.getErrorType().getMessage());
                }
            });
        }
        else
        {
            AccountKit.logOut();

            //Clear all activity
            Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    /*private void chooseImage() {
        startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(), "Select an image"),
                PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data != null) {
                    selectedFileUri = data.getData();
                    if (selectedFileUri != null && !selectedFileUri.getPath().isEmpty()) {
                        user_profile_photo.setImageURI(selectedFileUri);
                        uploadFile();
                    } else
                        Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void uploadFile() {
        if (selectedFileUri != null) {
            File file = FileUtils.getFile(this, selectedFileUri);

            String fileName = new StringBuilder(Common.currentUser.getPhone())
                    .append(FileUtils.getExtension(file.toString()))
                    .toString();

            ProgressRequestBody requestFile = new ProgressRequestBody(file, (UploadCallBack) this); // CHANGES

            final MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", fileName, requestFile);

            final MultipartBody.Part userPhone = MultipartBody.Part.createFormData("phone", Common.currentUser.getPhone());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mService.uploadFile(userPhone, body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Toast.makeText(ProfileActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }).start();

        }
    }*/

    //QR
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(this,"You cancelled the scanning",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
