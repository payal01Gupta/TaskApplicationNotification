package com.example.taskapplication.FirebaseClasses;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.taskapplication.worker.VideoMediaStoreWorker;
import com.example.taskapplication.worker.VodNameApiWorker;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class DataListFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {

            String data = remoteMessage.getData().get("hitApi");
            Log.e("PAYAL", data.toString());

            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            if(data.equalsIgnoreCase("true")) {
                OneTimeWorkRequest request =
                        new OneTimeWorkRequest.Builder(VodNameApiWorker.class)
                                .setConstraints(constraints)
                                .build();

                WorkManager.getInstance(this).enqueue(request);
            }
        }
    }
}
