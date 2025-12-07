package com.example.taskapplication.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskapplication.R;
import com.example.taskapplication.service.MusicService;

public class MusicPlayerActivity extends AppCompatActivity {
    Button btnPlay, btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);

        btnPlay.setOnClickListener(v -> {
            Intent i = new Intent(MusicPlayerActivity.this, MusicService.class);
            i.setAction("PLAY");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(i);
            else
                startService(i);
        });

        btnStop.setOnClickListener(v -> {
            Intent i = new Intent(MusicPlayerActivity.this, MusicService.class);
            i.setAction("STOP");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(i);
            else
                startService(i);
        });
    }
}