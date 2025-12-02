// MainActivity.java
package com.example.taskapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.viewModel.JokeViewModel;
import com.example.taskapplication.worker.ApiWorker;
import com.example.taskapplication.worker.VideoMediaStoreWorker;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class DemoActivity extends AppCompatActivity {

    private JokeViewModel viewModel;
    private TextView textView;
    private Button button;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FCM_TOKEN", task.getResult());
                    }
                });

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(this).get(JokeViewModel.class);

        // Observe joke text
        viewModel.getJokeText().observe(this, joke -> textView.setText(joke));

        // Observe loading state
        viewModel.getIsLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
                button.setEnabled(false);
            } else {
                progressBar.setVisibility(View.GONE);
                button.setEnabled(true);
            }
        });

        button.setOnClickListener(v -> viewModel.fetchJoke());

 //       callHourlyNotificationWorker();


     //   workertask();

    }

    public void workertask(){
        File destFolder = new File(getApplicationContext().getExternalFilesDir(null), "AppName");
        Log.d("VIDEO_PATH", destFolder.getAbsolutePath());
        if (!destFolder.exists()) destFolder.mkdirs();

        File destFile = new File(destFolder, "payal.mp4");

        try {
                // Android 10+ → use URI (no file path)
                Uri uri = findVideoUri("payal.mp4");
                Log.e("PAYAL",uri.toString());

                if (uri == null) {
                    Log.e("Worker", "Video not found (Android 10+)");
                }

                copyFileUsingUri(uri, destFile);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private Uri findVideoUri(String fileName) {

        Uri collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);

        String selection = MediaStore.Video.Media.DISPLAY_NAME + "=?";
        String[] args = new String[]{ fileName };

        Cursor cursor = getApplicationContext().getContentResolver()
                .query(collection, new String[]{MediaStore.Video.Media._ID}, selection, args, null);

        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
            cursor.close();

            return Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + id);
        }

        if (cursor != null) cursor.close();
        return null;
    }


    private void copyFileUsingUri(Uri srcUri, File dst) throws Exception {

        ContentResolver resolver = getApplicationContext().getContentResolver();

        try (InputStream in = resolver.openInputStream(srcUri);
             OutputStream out = new FileOutputStream(dst)) {

            byte[] buffer = new byte[4096];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void callHourlyNotificationWorker() {

        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(
                        ApiWorker.class,
                        15, TimeUnit.MINUTES
                )
                  //      .setInitialDelay(15, TimeUnit.MINUTES) // first notification after 1 hr (optional)
                        .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "hourly_work",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
        );

    }

}
