package com.example.taskapplication.worker;

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

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VodNameApiWorkerOne extends Worker {
    public VodNameApiWorkerOne(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("PAYAL", "Worker started");
        Retrofit retrofitObject = Utils.retrofitObject(getApplicationContext());
        if (retrofitObject != null) {
            ApiService service = retrofitObject.create(ApiService.class);
            Call<List<VodStreamsCallback>> call = service.allVODStreams(AppConst.CONTENT_TYPE, "f19802025", "@f19802025","get_vod_streams");
            try {
                Response response=call.execute();
                if(response.isSuccessful()){
                    Log.e("PAYAL", "API success on Response");
                        List<VodStreamsCallback> dataList = (List<VodStreamsCallback>) response.body();
                        if(dataList != null && !dataList.isEmpty()){
                            saveToPrefs(dataList);
                            sendBroadcast();
                        }
                  Result.success();
                }else{
                    Log.e("PAYAL", "API success on Failure");
                    Result.failure();
                }
            } catch (IOException e) {
                Log.e("PAYAL", "API Exception");
                Result.retry();
            }
        }
        Log.e("PAYAL", "final Result");
        return Result.failure();
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
