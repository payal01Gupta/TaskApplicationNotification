package com.example.taskapplication.worker;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VideoDownloadWorker extends Worker {
    public VideoDownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

            String videoName = getInputData().getString("videoname");
            Log.e("PAYAL",videoName.toString());
            if (videoName == null) return Result.failure();

            // SEARCH FULL STORAGE FOR THE VIDEO
            File sourceFile = searchFileRecursively(Environment.getExternalStorageDirectory(), videoName);

            if (sourceFile == null) {
                Log.e("PAYAL", "Video NOT FOUND: " + videoName);
                return Result.failure();
            }

            Log.e("PAYAL", "FOUND at: " + sourceFile.getAbsolutePath());

            // Create output folder
            File destFolder = new File(Environment.getExternalStorageDirectory(), "MySavedVideos");
            if (!destFolder.exists()) destFolder.mkdirs();

            File destFile = new File(destFolder, videoName);

            try {
                copyFile(sourceFile, destFile);
                Log.e("PAYAL", "Copied to: " + destFile.getAbsolutePath());
                return Result.success();
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        // 🔥 Deep recursive search (searches everywhere)
        private File searchFileRecursively(File dir, String targetName) {
            File[] files = dir.listFiles();
            if (files == null) return null;

            for (File f : files) {

                // If FILE and name matches → return it
                if (f.isFile() && f.getName().equalsIgnoreCase(targetName)) {
                    return f;
                }

                // If folder → go inside and search
                if (f.isDirectory()) {
                    File found = searchFileRecursively(f, targetName);
                    if (found != null) return found;
                }
            }
            return null;
        }

        // Copy method
        private void copyFile(File src, File dst) throws IOException {
            try (FileInputStream in = new FileInputStream(src);
                 FileOutputStream out = new FileOutputStream(dst)) {

                byte[] buffer = new byte[4096];
                int len;

                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            }
        }

       /* String videoName = getInputData().getString("videoname");
        if (videoName == null) return Result.failure();

        File source = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Movies"+"/Instagram", videoName);
        Log.e("payal", source.toPath().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        }

        if (!source.exists()) {
            return Result.failure();
        }

        File destFolder = new File(getApplicationContext().getExternalFilesDir(null), "AppName");
        if (!destFolder.exists()) destFolder.mkdirs();

        File destFile = new File(destFolder, videoName);
        Log.e("payal1",destFile.getPath().toString());

        try {
            copyFile(source, destFile);
        } catch (Exception e) {
            return Result.failure();
        }

        return Result.success();*/

//    private void copyFile(File src, File dst) throws IOException {
//        try (InputStream in = new FileInputStream(src);
//             OutputStream out = new FileOutputStream(dst)) {
//
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = in.read(buffer)) > 0) {
//                out.write(buffer, 0, length);
//            }
//        }
//    }
}
