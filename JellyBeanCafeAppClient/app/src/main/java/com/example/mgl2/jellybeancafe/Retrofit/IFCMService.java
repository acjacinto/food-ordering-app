package com.example.mgl2.jellybeancafe.Retrofit;

import com.example.mgl2.jellybeancafe.Model.DataMessage;
import com.example.mgl2.jellybeancafe.Model.MyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers(
            {

            "Content-Type:application/json",
            "Authorization:key=AAAANJxgQGY:APA91bEmTLs_FDIpR6zJnfZFkr6XlF9RK2pBfatFkKBO9zrt5g5zZOHy8jK0ZQThGP5CV-_ClVqQCw22Un7Z0CcdG9FGcT2iT6D4lsZw4WG8guiXrzZtF_gJZX1fzvHLGkm4G79m9J0-"
    }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body DataMessage body);
}
