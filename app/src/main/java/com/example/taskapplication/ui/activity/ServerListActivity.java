package com.example.taskapplication.ui.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.adapter.GroupAdapter;
import com.example.taskapplication.ui.adapter.ServerAdapter;
import com.example.taskapplication.ui.model.ServersModel;
import com.example.taskapplication.ui.viewModel.ServerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ServerListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ServerAdapter adapter;
    ServerViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ServerAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        String group = getIntent().getStringExtra("group");

        viewModel = new ViewModelProvider(this).get(ServerViewModel.class);

        viewModel.getGroupServerList().observe(this, map -> {
            List<ServersModel> servers = map.get(group);
            if (servers != null) {
                adapter.updateList(servers);
            }
        });
        viewModel.loadServers();
    }
}