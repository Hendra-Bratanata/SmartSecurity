package com.tugasakhir.smartsecurity.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.tugasakhir.smartsecurity.R;

public class Notif {
    private int notifID = 1001;
    private String chID = "ch_id";
    private String chName = "notif";

    public void sendNotif(String user, String informasi, Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, chID)
                .setSmallIcon(R.drawable.ic_priority_high_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_priority_high_black_24dp))
                .setContentTitle(user)
                .setContentTitle(informasi)
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(chID, chName,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(chName);
            builder.setChannelId(chID);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();

        if (notificationManager != null){
            notificationManager.notify(notifID,notification);
        }
    }

    public void ShowNotification(Context applicationContext, String information, String now, String date, int i) {
    }

    public void sendNotif(Context applicationContext, String information, String now, String date, int i) {
    }
}
