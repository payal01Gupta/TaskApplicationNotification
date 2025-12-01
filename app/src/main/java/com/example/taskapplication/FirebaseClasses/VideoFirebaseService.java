package com.example.taskapplication.FirebaseClasses;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.taskapplication.worker.VideoDownloadWorker;
import com.example.taskapplication.worker.VideoMediaStoreWorker;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class VideoFirebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {

            String videoName = remoteMessage.getData().get("videoname");

            Log.e("PAYAL", videoName.toString());
            Data data = new Data.Builder()
                    .putString("videoname", videoName)
                    .build();

            OneTimeWorkRequest request =
                    new OneTimeWorkRequest.Builder(VideoMediaStoreWorker.class)
                            .setInputData(data)
                            .build();

            WorkManager.getInstance(this).enqueue(request);
        }
    }

}
