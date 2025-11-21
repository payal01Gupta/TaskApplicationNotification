// MainActivity.java
package com.example.taskapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.viewModel.JokeViewModel;
import com.google.firebase.messaging.FirebaseMessaging;

public class DemoActivity extends AppCompatActivity {

    private JokeViewModel viewModel;
    private TextView textView;
    private Button button;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FCM_TOKEN", task.getResult());
                    }
                });

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(this).get(JokeViewModel.class);

        // Observe joke text
        viewModel.getJokeText().observe(this, joke -> textView.setText(joke));

        // Observe loading state
        viewModel.getIsLoading().observe(this, loading -> {
            if (loading) {
                progressBar.setVisibility(View.VISIBLE);
                button.setEnabled(false);
            } else {
                progressBar.setVisibility(View.GONE);
                button.setEnabled(true);
            }
        });

        button.setOnClickListener(v -> viewModel.fetchJoke());
    }
}
