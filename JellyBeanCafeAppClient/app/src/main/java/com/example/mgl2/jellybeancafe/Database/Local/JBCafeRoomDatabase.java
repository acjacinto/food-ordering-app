package com.example.mgl2.jellybeancafe.Database.Local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mgl2.jellybeancafe.Database.ModelDB.Cart;
import com.example.mgl2.jellybeancafe.Database.ModelDB.Favorite;

@Database(entities = {Cart.class, Favorite.class},version = 1,exportSchema = false)
public abstract class JBCafeRoomDatabase extends RoomDatabase{

    public abstract CartDAO cartDAO();
    public abstract FavoriteDao favoriteDao();

    private static JBCafeRoomDatabase instance;

    public static JBCafeRoomDatabase getInstance(Context context)
    {
        if(instance == null)
            instance = Room.databaseBuilder(context,JBCafeRoomDatabase.class,"JBCAFE_OrderingAppDB")
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
