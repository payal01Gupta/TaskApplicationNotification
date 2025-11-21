package com.example.taskapplication.FirebaseClasses;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.taskapplication.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("rrrrr","onMessgeRecieved");

//         SIMPLE NOTIFICATIO
        try {
            if (remoteMessage.getNotification() != null) {
                String title = remoteMessage.getNotification().getTitle();
                String body = remoteMessage.getNotification().getBody();

                if (remoteMessage.getData().size() > 0) {
                    String msg = remoteMessage.getData().get("message");
                    String user = remoteMessage.getData().get("user");
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    showDataNotification("Data Message", msg ," (From: " + user + ")");
                }else{
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    showNotification(title, body);
                }
            }

            /*// DATA NOTIFICATION
            if (remoteMessage.getData().size() > 0) {
                String msg = remoteMessage.getData().get("message");
                String user = remoteMessage.getData().get("user");
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                showNotification("Data Message", msg + " (From: " + user + ")");
            }*/

        }catch (Exception e) {
                e.printStackTrace();
                Log.e("rrrrr",e.getMessage());
            }
        }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void showDataNotification(String title, String message, String user) {
        String channelId = "my_channel";

        NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Data Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(message + "extra parameter" +user)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void showNotification(String title, String message) {
        String channelId = "my_channel";

        NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Data Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            nm.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

        if (intent != null && intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
                if(key.equalsIgnoreCase("message") ) {
                    if(value != null) {
                        String message = value.toString();
                        showDataNotification("Data Message", message );
                    }
                    break;
                } else {
                    showNotification(title, body);
                }
            }
//            if(intent.getExtras() != null){
//                String title = "", body = "", image = "", custombody = "";
//                if(intent.hasExtra("gcm.notification.title")) {
//                    title = intent.getStringExtra("gcm.notification.title");
//                }
//                if(intent.hasExtra("gcm.notification.body")) {
//                    body = intent.getStringExtra("gcm.notification.body");
//                }
//                if(intent.hasExtra("gcm.notification.image")) {
//                    image = intent.getStringExtra("gcm.notification.image");
//                }
//                if(intent.hasExtra("custombody")) {
//                    custombody = intent.getStringExtra("custombody");
//                }
//                if(title != null && title.length() > 0 && body != null && body.length() > 0){
//                    if(image != null && image.length() > 0) {
//                        notificationUtils.showNotificationMessage(title, body, "", resultIntent, image, custombody);
//                    } else {
//                        notificationUtils.showNotificationMessage(title, body, "", resultIntent, "", custombody);
//                    }
//                    return;
//                }
//            }
        }

        String message = intent.getStringExtra("message");
        String user = intent.getStringExtra("user");
    }
}

