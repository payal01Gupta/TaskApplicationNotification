package com.example.taskapplication.ui.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.viewModel.LiveDataViewModel;
import com.example.taskapplication.ui.viewModel.ServerViewModel;

public class LiveDataActivity extends AppCompatActivity {

    LiveDataViewModel liveDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        liveDataViewModel = new ViewModelProvider(LiveDataActivity.this).get(LiveDataViewModel.class);
    }
}