package com.example.taskapplication.worker;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.taskapplication.miscellanious.AppConst;
import com.example.taskapplication.miscellanious.Utils;
import com.example.taskapplication.ui.webServices.ApiService;
import com.example.taskapplication.ui.webServices.VodStreamsCallback;

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

        Retrofit retrofitObject = Utils.retrofitObject(getApplicationContext());
        if (retrofitObject != null) {
            ApiService service = retrofitObject.create(ApiService.class);
            Call<List<VodStreamsCallback>> call = service.allVODStreams(AppConst.CONTENT_TYPE, "Abel57", "222333","get_vod_streams");
            call.enqueue(new Callback<List<VodStreamsCallback>>() {
                @Override
                public void onResponse(Call<List<VodStreamsCallback>> call, Response<List<VodStreamsCallback>> response) {
                    if(response != null && response.isSuccessful()){
                        List<VodStreamsCallback> dataList = response.body();
                    }
                    Result.success();
                }

                @Override
                public void onFailure(Call<List<VodStreamsCallback>> call, Throwable t) {
                    Result.failure();
                }
            });
        }
        return Result.retry();
        }
}
