package com.example.taskapplication.worker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.taskapplication.miscellanious.AppConst;
import com.example.taskapplication.miscellanious.Utils;
import com.example.taskapplication.ui.webServices.ApiService;
import com.example.taskapplication.ui.webServices.VodStreamsCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VodNameApiWorkerTwo extends ListenableWorker {

    private final SettableFuture<Result> future = SettableFuture.create();
    public VodNameApiWorkerTwo(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {

        Retrofit retrofitObject = Utils.retrofitObject(getApplicationContext());
        if (retrofitObject != null) {
            ApiService service = retrofitObject.create(ApiService.class);
            Call<List<VodStreamsCallback>> call = service.allVODStreams(AppConst.CONTENT_TYPE, "f19802025", "@f19802025","get_vod_streams");
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
                        future.set(Result.success());
                    }else{
                        future.set(Result.failure());
                    }

                }

                @Override
                public void onFailure(Call<List<VodStreamsCallback>> call, Throwable t) {
                    Log.e("PAYAL", "API failed: " + t.getMessage().toString());
                    Result.failure();
                }
            });
        }



        return future;
    }


    private void saveToPrefs(List<VodStreamsCallback> vodList) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(vodList);
        prefs.edit().putString("vod_streams", json).apply();
        Log.e("PAYAL", "Prefrence update");
    }

    private void sendBroadcast() {
        Intent intent = new Intent(AppConst.NOTIFICATION_BROADCAST);
        getApplicationContext().sendBroadcast(intent);
    }
}
