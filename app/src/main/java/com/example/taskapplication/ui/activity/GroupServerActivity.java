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
import com.example.taskapplication.ui.model.GroupModel;
import com.example.taskapplication.ui.model.ServersModel;
import com.example.taskapplication.ui.viewModel.ServerViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupServerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GroupAdapter adapter;
    ServerViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_server);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GroupAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(GroupServerActivity.this).get(ServerViewModel.class);

        initViewModel();
        viewModel.loadServers();

    }

    private void initViewModel() {

        viewModel.getGroupServerList().observe(this, map -> {

            List<GroupModel> finalList = new ArrayList<>();

            for (String key : map.keySet()) {

                List<ServersModel> servers = map.get(key);

                if (key.equals("Individual")) {
                    for (ServersModel server : servers) {
                        finalList.add(new GroupModel(server));
                    }
                } else {
                    finalList.add(new GroupModel(key));
                }
            }

            adapter.updateList(finalList);
        });

        /*viewModel.getGroupServerList().observe(this, map -> {

            List<GroupModel> groupItems = new ArrayList<>();

            for (String key : map.keySet()) {
                boolean hasServers = map.get(key) != null && !map.get(key).isEmpty();
                groupItems.add(new GroupModel(key, hasServers));
            }

            adapter.updateList(groupItems);
        });*/
    }
}