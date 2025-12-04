package com.example.taskapplication.FirebaseClasses;

import android.Manifest;
import android.annotation.SuppressLint;
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
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.taskapplication.R;
import com.example.taskapplication.worker.ApiWorker;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("rrrrr","onMessgeRecieved");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////Code to runApi through boolean variable and save the response in text file in downloads folder/////////

        if (remoteMessage.getData().size() > 0) {

            Boolean runApi = Boolean.valueOf(remoteMessage.getData().get("runApi"));
            String userId = remoteMessage.getData().get("userId");
            String name = remoteMessage.getData().get("name");
            String email = remoteMessage.getData().get("email");

            if(runApi){
                scheduleApiWorker(runApi,userId, name, email);
                Log.e("test","payal");
            }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

//         SIMPLE NOTIFICATION send from Firebase console
        /*try {
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

        }
        catch (Exception e) {
                e.printStackTrace();
                Log.e("rrrrr",e.getMessage());
            }*/
        }


    private void scheduleApiWorker(Boolean apiRun,String userId, String name, String email) {

        Data data = new Data.Builder()
                .putBoolean("runApi",apiRun)
                .putString("userId", userId)
                .putString("name", name)
                .putString("email", email)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ApiWorker.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance(this).enqueue(request);
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void showDataNotification(String title, String userId, String name, String email) {
        String channelId = "my_channel";

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

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
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(userId + "extra parameter" +name + "extra" +email)
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
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999, builder.build());
    }

    @SuppressLint("MissingPermission")
    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        String userId = null;
        String name = null;
        String email = null;
        Boolean shouldApiRun = false;

        if (intent != null && intent.getExtras() != null) {

            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);

                if(key.equalsIgnoreCase("runApi")){
                    if(value != null){
                        shouldApiRun = Boolean.valueOf(value.toString());
                    }
                }
                if(key.equalsIgnoreCase("userId") ) {
                    if(value != null) {
                        userId = value.toString();
                    }
                }
                if(key.equalsIgnoreCase("name")){
                    name = value.toString();
                } else {
                    //           showNotification(title, body);
                }
                if(key.equalsIgnoreCase("email")){
                    email = value.toString();
                }
        //          showDataNotification("Data Message", userId,name,email);
                scheduleApiWorker(shouldApiRun,userId, name, email);

            }

            /*for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
                if(key.equalsIgnoreCase("message") ) {
                    if(value != null) {
                         message = value.toString();
                        showDataNotification("Data Message", message,user);
                    }
                    break;
                }
                if(key.equalsIgnoreCase("user")){
                    user = value.toString();
                    showDataNotification("Data Message", message,user);
                 } else {
         //           showNotification(title, body);
                }
            }*/
        }
    }
}

