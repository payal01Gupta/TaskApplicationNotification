package com.example.taskapplication.ui.activity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.Receiver.VodReceiver;
import com.example.taskapplication.miscellanious.AppConst;
import com.example.taskapplication.ui.adapter.VodListAdapter;
import com.example.taskapplication.ui.webServices.VodStreamsCallback;
import com.example.taskapplication.worker.VodNameApiWorker;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VodDataListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    VodListAdapter adapter;
    VodReceiver receiver;
    LinearLayout ll_recyclerView;
    TextView tv_noDataFound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod_data_list);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FCM_TOKEN", task.getResult());
                    }
                });

        recyclerView = findViewById(R.id.recyclerView);
        ll_recyclerView = findViewById(R.id.ll_recyclerView);
        tv_noDataFound = findViewById(R.id.tv_noDataFound);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initial load
        List<VodStreamsCallback> list = loadFromPrefs();
        if(list!=null && !list.isEmpty()) {
            ll_recyclerView.setVisibility(VISIBLE);
            tv_noDataFound.setVisibility(GONE);

            adapter = new VodListAdapter(list);
            recyclerView.setAdapter(adapter);
        }else{
            tv_noDataFound.setVisibility(VISIBLE);
            ll_recyclerView.setVisibility(GONE);

        }



        // register broadcast receiver
        receiver = new VodReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (AppConst.NOTIFICATION_BROADCAST.equals(intent.getAction())) {
                    List<VodStreamsCallback> updatedList = loadFromPrefs();
                    adapter.updateList(updatedList);
                }
            }
        };

        IntentFilter filter = new IntentFilter(AppConst.NOTIFICATION_BROADCAST);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
        }
    }

    private List<VodStreamsCallback> loadFromPrefs() {
        SharedPreferences prefs = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        String json = prefs.getString("vod_streams", null);

        if (json == null) return new ArrayList<>();

        Gson gson = new Gson();
        Type type = new TypeToken<List<VodStreamsCallback>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}