package com.example.taskapplication.FirebaseClasses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.taskapplication.worker.VideoMediaStoreWorker;
import com.example.taskapplication.worker.VodNameApiWorker;
import com.example.taskapplication.worker.VodNameApiWorkerOne;
import com.example.taskapplication.worker.VodNameApiWorkerTwo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class DataListFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        clearPrefrences();
        Log.e("PAYAl", "onMessageReceived called");
//        if (remoteMessage.getData().size() > 0) {
//
//            String data = remoteMessage.getData().get("hitApi");
//            Log.e("PAYAL", data.toString());
//
//            Constraints constraints = new Constraints.Builder()
//                    .setRequiredNetworkType(NetworkType.CONNECTED)
//                    .build();
//
//            if(data.equalsIgnoreCase("true")) {
//                OneTimeWorkRequest request =
//                        new OneTimeWorkRequest.Builder(VodNameApiWorker.class)
//                                .setConstraints(constraints)
//                                .build();
//
//                WorkManager.getInstance(this).enqueue(request);
//            }
//        }
    }

    @Override
    public void handleIntent(Intent intent) {
//        super.handleIntent(intent);

        clearPrefrences();
        Log.e("PAYAl", "handleIntent called");
        if(intent != null && intent.getExtras() != null){

            String runApi="";
            if(intent.hasExtra("hitApi")) {
                 runApi = intent.getStringExtra("hitApi");
            }
            Log.e("PAYAl", runApi);

            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();


            if(runApi.equalsIgnoreCase("true")){
                OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(VodNameApiWorkerTwo.class)
                        .setConstraints(constraints)
                        .build();
                WorkManager.getInstance(this).enqueue(request);
//                return;
            }
        }
        super.handleIntent(intent);
    }


    private void clearPrefrences(){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();


    }
}
