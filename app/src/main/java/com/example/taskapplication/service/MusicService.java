package com.example.taskapplication.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.taskapplication.R;
import com.example.taskapplication.ui.activity.MusicPlayerActivity;

public class MusicService extends Service {

    MediaPlayer mediaPlayer;
    int[] songs = {
            R.raw.chandrachooda_shiva_shankara,
            R.raw.gallan_do,
            R.raw.jab_koi_baat_bigad_jaaye,
            R.raw.raabta,
            R.raw.ranjheya_ve_zain_zohaib,
            R.raw.tu_te_main,
            R.raw.tum_ho_toh_saiyaara
    };

    String[] songNames = {
            "Chandrachooda Shiva Shankara",
            "Gallan Do",
            "Jab Koi Baat Bigad Jaaye",
            "Raabta",
            "Ranjheya Ve – Zain Zohaib",
            "Tu Te Main",
            "Tum Ho Toh Saiyaara"
    };

    int currentIndex = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case "PLAY":
                    playSong();
                    break;

                case "NEXT":
                    nextSong();
                    break;

                case "PREV":
                    previousSong();
                    break;

                case "STOP":
                    stopMusic();
                    break;
            }
        }

        return START_STICKY;
    }

    private void playSong() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        mediaPlayer = MediaPlayer.create(this, songs[currentIndex]);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> nextSong());

        String currentSongName = songNames[currentIndex];
        showNotification(currentSongName);
    }

    private void nextSong() {
        currentIndex = (currentIndex + 1) % songs.length;
        playSong();
    }

    private void previousSong() {
        currentIndex = (currentIndex - 1 + songs.length) % songs.length;
        playSong();
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopForeground(true);
        stopSelf();
    }

    private void showNotification(String songName) {

        String channelId = "music_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Music Player",
                    NotificationManager.IMPORTANCE_LOW
            );
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

        PendingIntent prevIntent = PendingIntent.getService(
                this, 1,
                new Intent(this, MusicService.class).setAction("PREV"),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        PendingIntent nextIntent = PendingIntent.getService(
                this, 2,
                new Intent(this, MusicService.class).setAction("NEXT"),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        PendingIntent stopIntent = PendingIntent.getService(
                this, 3,
                new Intent(this, MusicService.class).setAction("STOP"),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Intent activityIntent = new Intent(this, MusicPlayerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this, 0, activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle("Now Playing")
                .setContentText(songName)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .addAction(R.drawable.ic_prev, "Prev", prevIntent)
                .addAction(R.drawable.ic_next, "Next", nextIntent)
                .addAction(R.drawable.ic_stop, "Stop", stopIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 2, 1))
                .build();

        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
