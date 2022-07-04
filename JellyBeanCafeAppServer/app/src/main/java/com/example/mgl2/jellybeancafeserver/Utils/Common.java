package com.example.mgl2.jellybeancafeserver.Utils;

import com.example.mgl2.jellybeancafeserver.Model.Category;
import com.example.mgl2.jellybeancafeserver.Model.Drink;
import com.example.mgl2.jellybeancafeserver.Model.Order;
import com.example.mgl2.jellybeancafeserver.Retrofit.FCMRetrofitClient;
import com.example.mgl2.jellybeancafeserver.Retrofit.IFCMServices;
import com.example.mgl2.jellybeancafeserver.Retrofit.IJBCafeAPI;
import com.example.mgl2.jellybeancafeserver.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static Category currentCategory;
    public static Drink currentDrink;
    public static Order currentOrder;

    public static List<Category> menuList = new ArrayList<>();

    //In Emulator , localhost = 10.0.2.2
    /*public static final String BASE_URL = "http://192.168.254.102/jellybeancafe/";*/
    public static final String BASE_URL = "http://192.168.1.72/jellybeancafe/";

    public static final String FCM_URL = "https://fcm.googleapis.com/";

    public static IJBCafeAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IJBCafeAPI.class);
    }

    public static IFCMServices getFCMAPI()
    {
        return FCMRetrofitClient.getClient(FCM_URL).create(IFCMServices.class);
    }

    public static String convertCodeToStatus(int orderStatus) {
        switch (orderStatus)
        {
            case 0:
                return "Placed";
            case 1:
                return "Processing";
            case 2:
                return "Shipping";
            case 3:
                return "Shipped";
            case -1:
                return "Cancelled";
            default:
                return "Order Error";
        }
    }
}
