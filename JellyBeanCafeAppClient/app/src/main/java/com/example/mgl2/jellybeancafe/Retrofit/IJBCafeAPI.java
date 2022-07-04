package com.example.mgl2.jellybeancafe.Retrofit;

import com.example.mgl2.jellybeancafe.Model.Banner;
import com.example.mgl2.jellybeancafe.Model.Category;
import com.example.mgl2.jellybeancafe.Model.CheckUserResponse;
import com.example.mgl2.jellybeancafe.Model.Drink;
import com.example.mgl2.jellybeancafe.Model.Order;
import com.example.mgl2.jellybeancafe.Model.OrderResult;
import com.example.mgl2.jellybeancafe.Model.Store;
import com.example.mgl2.jellybeancafe.Model.Token;
import com.example.mgl2.jellybeancafe.Model.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IJBCafeAPI {
    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkExistsUser(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("checkuseraccount.php")
    Call<CheckUserResponse> checkExistsUserAccount(@Field("email") String email,
                                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> registerNewUser(@Field("phone") String phone,
                               @Field("name") String name,
                               @Field("birthdate") String birthdate,
                               @Field("email") String email,
                               @Field("password") String password,
                               @Field("address") String address,
                               @Field("barangay") String barangay);

    @FormUrlEncoded
    @POST("getdrink.php")
    Observable<List<Drink>> getDrinkByMenuID(@Field("menuid") String menuID);


    @FormUrlEncoded
    @POST("getuser.php")
    Call<User> getUserInformation(@Field("phone") String phone);

    @GET("getbanner.php")
    Observable<List<Banner>> getBanners();

    @GET("getmenu.php")
    Observable<List<Category>> getMenu();

    @Multipart
    @POST("upload.php")
    Call<String> uploadFile(@Part MultipartBody.Part phone, @Part MultipartBody.Part file);

    @GET("getalldrinks.php")
    Observable<List<Drink>> getAllDrinks();


    @FormUrlEncoded
    @POST("submitorder.php")
    Call<OrderResult> submitOrder(@Field("price") float orderPrice,
                                  @Field("orderDetail") String orderDetail,
                                  @Field("comment") String comment,
                                  @Field("address") String address,
                                  @Field("phone") String phone,
                                  @Field("paymentMethod") String paymentMethod);

    @FormUrlEncoded
    @POST("braintree/checkout.php")
    Call<String> payment(@Field("nonce") String nonce,
                             @Field("amount") String amount);

    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<String> updateToken(@Field("phone") String phone,
                             @Field("token") String token,
                             @Field("isServerToken") String isServerToken);

    @FormUrlEncoded
    @POST("getorder.php")
    Observable<List<Order>> getOrder(@Field("userPhone") String userPhone,
                                     @Field("status") String status);

    @FormUrlEncoded
    @POST("getnearbystore.php")
    Observable<List<Store>> getNearbyStore(@Field("lat") String lat,
                                           @Field("lng") String lng);

    @FormUrlEncoded
    @POST("gettoken.php")
    Call<Token> getToken(@Field("phone") String phone,
                         @Field("isServerToken") String isServerToken);
}
