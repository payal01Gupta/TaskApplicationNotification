package com.example.taskapplication.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.Receiver.VodReceiver;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // initial load
        List<VodStreamsCallback> list = loadFromPrefs();
        adapter = new VodListAdapter(list);
        recyclerView.setAdapter(adapter);

        // register broadcast receiver
        receiver = new VodReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("ACTION_VOD_UPDATE".equals(intent.getAction())) {
                    List<VodStreamsCallback> updatedList = loadFromPrefs();
                    adapter.updateList(updatedList);
                }
            }
        };

        IntentFilter filter = new IntentFilter("ACTION_VOD_UPDATE");
        registerReceiver(receiver, filter);
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