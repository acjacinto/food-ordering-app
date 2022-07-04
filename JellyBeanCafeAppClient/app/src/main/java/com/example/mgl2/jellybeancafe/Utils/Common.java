package com.example.mgl2.jellybeancafe.Utils;

import com.example.mgl2.jellybeancafe.Database.DataSource.CartRepository;
import com.example.mgl2.jellybeancafe.Database.DataSource.FavoriteRepository;
import com.example.mgl2.jellybeancafe.Database.Local.JBCafeRoomDatabase;
import com.example.mgl2.jellybeancafe.Model.Category;
import com.example.mgl2.jellybeancafe.Model.Drink;
import com.example.mgl2.jellybeancafe.Model.Order;
import com.example.mgl2.jellybeancafe.Model.User;
import com.example.mgl2.jellybeancafe.Retrofit.FCMClient;
import com.example.mgl2.jellybeancafe.Retrofit.IFCMService;
import com.example.mgl2.jellybeancafe.Retrofit.IJBCafeAPI;
import com.example.mgl2.jellybeancafe.Retrofit.RetrofitClient;
import com.example.mgl2.jellybeancafe.Retrofit.RetrofitScalarsClient;

import java.util.ArrayList;
import java.util.List;

public class Common {
    //In Emulator , localhost = 10.0.2.2
    /*public static final String BASE_URL = "http://192.168.254.102/jellybeancafe/";*/
    public static final String BASE_URL = "http://192.168.1.72/jellybeancafe/";

    public static final String API_TOKEN_URL = "http://192.168.1.72/jellybeancafe/braintree/main.php";
    /*public static final String API_TOKEN_URL = "http://192.168.1.72/jellybeancafe/braintree/main.php";*/

    public static final String TOPPING_MENU_ID = "11";


    public static User currentUser = null;
    public static Category currentCategory = null;
    public static Order currentOrder=null;

    public static List<Drink> toppingList = new ArrayList<>();

    public static double toppingPrice=0.0;
    public static List<String> toppingAdded=new ArrayList<>();

    //Hold field
    public static int sizeOfCup = 0; //-1 : no choice (error) , 0 : S , 1 : M , 2 : L
    public static int sugar = 100; // -1 : no choice (error)


    //Database
    public static JBCafeRoomDatabase jbCafeRoomDatabase;
    public static CartRepository cartRepository;
    public static FavoriteRepository favoriteRepository;

    private static final String FCM_API = "https://fcm.googleapis.com/";

    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(FCM_API).create(IFCMService.class);
    }

    public static IJBCafeAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IJBCafeAPI.class);
    }

    public static IJBCafeAPI getScalarsAPI()
    {
        return RetrofitScalarsClient.getScalarsClient(BASE_URL).create(IJBCafeAPI.class);
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

    public static String convertMenuId(int menuId){
        switch (menuId)
        {
            case 5:
                return "Frappe";
            case 6:
                return "Slush";
            case 8:
                return "Milk Tea";
            case 9:
                return "Hot Drink";
            case 11:
                return "Topping";
            case 12:
                return "Pizza";
            case 13:
                return "Cakes";
            case 14:
                return "Cupcakes";
            case 15:
                return "Floral Jelly";
            default:
                return "Order Error";
        }
    }

}
