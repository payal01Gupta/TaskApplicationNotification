package com.example.taskapplication.worker;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.model.User;
import com.example.taskapplication.ui.webServices.ApiService;
import com.example.taskapplication.ui.webServices.RetrofitClient;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import retrofit2.Response;

public class ApiWorker extends Worker {

    ApiService apiService;

    public ApiWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);

        apiService = RetrofitClient.getInstance().getApiService();
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @NonNull
    @Override
    public Result doWork() {
       // Boolean runApi = getInputData().getBoolean("runApi",false);
        String userId = getInputData().getString("userId");
        String name = getInputData().getString("name");
        String email = getInputData().getString("email");
      //  boolean shouldRunApi = Boolean.parseBoolean(runApi);

//        if (!runApi) {
//            return Result.success();
//        }

            User user = new User(userId, name, email);
            Log.e("user",user.toString());

            Log.e("rrrrr","doWork");

            try {
                Response<User> response = apiService.createUser(user).execute();

                if (response.isSuccessful()) {
                    Log.d("API_WORKER", "success");

                    InputStream inputStream = response.errorBody() != null
                            ? response.errorBody().byteStream()
                            : response.body() != null
                            ? new java.io.ByteArrayInputStream(
                            new Gson().toJson(response.body()).getBytes()
                    )
                            : null;

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }

                    reader.close();

                    String fullJsonResponse = sb.toString();
                    Log.d("API_WORKER", "Full response: " + fullJsonResponse);


                    Log.e("rrrrr","payal1");

                    // 2) Save JSON in internal storage
                    // -----------------------------
                    saveFileInternal("api_response.txt", fullJsonResponse);

                    Log.e("rrrrr","payal2");
                    // -----------------------------
                    // 3) Save JSON in Downloads folder
                    // -----------------------------
                    saveFileToDownloads("api_response.txt", fullJsonResponse);

                    Log.e("rrrrr","payal3");

                    showNotification("API Success", "File saved in Downloads");
                   // showNotification("API success", "User created" + response.body().toString());
                    return Result.success();
                } else {
                    Log.d("API_WORKER", "error");
                    return Result.retry();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("API_WORKER", "error");
                return Result.retry();
            }
    }

    // SAVE FILE TO INTERNAL STORAGE
    // ----------------------------------------------------
    private void saveFileInternal(String fileName, String content) {
        try {
            Log.e("test","payalnew");
            OutputStream os = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(content);
            writer.close();
            os.close();
        } catch (Exception e) {
            Log.e("FILE_SAVE", "Internal storage save error: " + e.getMessage());
        }
    }

    private void saveFileToDownloads(String fileName, String text) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
            values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");
            values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            OutputStream os = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                os = getApplicationContext()
                        .getContentResolver()
                        .openOutputStream(
                                getApplicationContext()
                                        .getContentResolver()
                                        .insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
                        );
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(text);
            writer.flush();
            writer.close();
            os.close();

        } catch (Exception e) {
            Log.e("FILE_SAVE", "Download save error: " + e.getMessage());
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void showNotification(String title, String message) {
        String channelId = "my_channel";
        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "API Worker Channel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(999, builder.build());
    }

    public void getDataTest(){
        Log.e("test1","payal22");
    }
}

