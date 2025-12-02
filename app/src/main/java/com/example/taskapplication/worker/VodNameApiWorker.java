package com.example.taskapplication.worker;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.taskapplication.miscellanious.AppConst;
import com.example.taskapplication.miscellanious.Utils;
import com.example.taskapplication.ui.webServices.ApiService;
import com.example.taskapplication.ui.webServices.VodStreamsCallback;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VodNameApiWorker extends Worker {
    public VodNameApiWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("PAYAL", "Worker started");
        Retrofit retrofitObject = Utils.retrofitObject(getApplicationContext());
        if (retrofitObject != null) {
            ApiService service = retrofitObject.create(ApiService.class);
            Call<List<VodStreamsCallback>> call = service.allVODStreams(AppConst.CONTENT_TYPE, "Abel57", "222333","get_vod_streams");
            call.enqueue(new Callback<List<VodStreamsCallback>>() {
                @Override
                public void onResponse(Call<List<VodStreamsCallback>> call, Response<List<VodStreamsCallback>> response) {
                    if(response != null && response.isSuccessful()){
                        Log.e("PAYAL", "API success: " + response.body().size());
                        List<VodStreamsCallback> dataList = response.body();
                        if(dataList != null && !dataList.isEmpty()){
                            saveToPrefs(dataList);
                            sendBroadcast();
                        }
                        Result.success();
                    }
                    Log.e("PAYAL", "API failed: " + response.code());
                    Result.failure();
                }

                @Override
                public void onFailure(Call<List<VodStreamsCallback>> call, Throwable t) {
                    Log.e("PAYAL", "API failed: " + t.getMessage().toString());
                    Result.failure();
                }
            });
        }
        return Result.retry();
        }

    private void saveToPrefs(List<VodStreamsCallback> vodList) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(vodList);
        prefs.edit().putString("vod_streams", json).apply();
    }

    private void sendBroadcast() {
        Intent intent = new Intent("ACTION_VOD_UPDATE");
        getApplicationContext().sendBroadcast(intent);
    }
}
