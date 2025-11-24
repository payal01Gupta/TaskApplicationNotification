package com.example.taskapplication.worker;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
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
        String userId = getInputData().getString("userId");
        String name = getInputData().getString("name");
        String email = getInputData().getString("email");

        User user = new User(userId, name, email);

        try {
            Response<User> response = apiService.createUser(user).execute();

            if (response.isSuccessful()) {
                Log.d("API_WORKER", "success");
                showNotification("API success","User created" + response.body().toString());
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
}

