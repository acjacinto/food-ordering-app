package com.example.mgl2.jellybeancafeserver.Utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import com.example.mgl2.jellybeancafeserver.R;

public class NotificationHelper extends ContextWrapper {
    private static final String JBCafe_CHANNEL_ID = "com.example.mgl2.jellybeancafeserver.JBCafe";
    private static final String JBCafe_CHANNEL_NAME = "Jelly Bean Cafe";

    private NotificationManager notificationManager;


    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel jbcafeChannel = new NotificationChannel(JBCafe_CHANNEL_ID,JBCafe_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        jbcafeChannel.enableLights(false);
        jbcafeChannel.enableVibration(true);
        jbcafeChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(jbcafeChannel);
    }

    public NotificationManager getManager() {
        if(notificationManager == null)
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getDrinkShopNotification(String title,
                                                         String message,
                                                         Uri soundUri)
    {
        return new Notification.Builder(getApplicationContext(),JBCafe_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(true);
    }
}
