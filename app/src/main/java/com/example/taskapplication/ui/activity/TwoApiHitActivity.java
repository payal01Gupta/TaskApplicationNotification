package com.example.taskapplication.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.adapter.UserAdapter;
import com.example.taskapplication.ui.viewModel.UserViewModel;

public class TwoApiHitActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    private UserAdapter adapter;
    private ProgressBar progressBar;
    private TextView messageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_api_hit);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        messageText = findViewById(R.id.messageText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        initViewModel();
    }
    private void initViewModel() {
        viewModel._isLoading().observe(this, loading->progressBar.setVisibility(loading ? View.VISIBLE : View.GONE));
        viewModel.getUser().observe(this, users -> adapter.setData(users));
        viewModel.getText().observe(this, messageText::setText);
    }
}