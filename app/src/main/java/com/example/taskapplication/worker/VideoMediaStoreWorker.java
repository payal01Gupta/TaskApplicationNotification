package com.example.taskapplication.worker;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class VideoMediaStoreWorker extends Worker {
    public VideoMediaStoreWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String videoName = getInputData().getString("videoname");
        Log.e("PAYAL", videoName.toString());
        if (videoName == null) return Result.failure();

        File destFolder = new File(getApplicationContext().getExternalFilesDir(null), "AppName");
        if (!destFolder.exists()) destFolder.mkdirs();

        File destFile = new File(destFolder, videoName);

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                // Android 9 and below → real path allowed
                String realPath = getRealPathBelowAndroid10(videoName);
                Log.e("PAYAL",realPath.toString());

                if (realPath == null) {
                    Log.e("Worker", "Video not found (below Android 10)");
                    return Result.failure();
                }

                File sourceFile = new File(realPath);
                if (!sourceFile.exists()) return Result.failure();

                copyFileUsingPath(sourceFile, destFile);

            } else {
                // Android 10+ → use URI (no file path)
                Uri uri = findVideoUri(videoName);
                Log.e("PAYAL",uri.toString());

                if (uri == null) {
                    Log.e("Worker", "Video not found (Android 10+)");
                    return Result.failure();
                }

                copyFileUsingUri(uri, destFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();

    }

    private String getRealPathBelowAndroid10(String fileName) {

        Uri collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Video.Media.DISPLAY_NAME + "=?";
        String[] args = new String[]{ fileName };

        String[] projection = {
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME
        };

        Cursor cursor = getApplicationContext().getContentResolver()
                .query(collection, projection, selection, args, null);

        if (cursor != null && cursor.moveToFirst()) {
            String fullPath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            cursor.close();
            return fullPath;
        }

        if (cursor != null) cursor.close();
        return null;
    }

    private Uri findVideoUri(String fileName) {

        Uri collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);

        String selection = MediaStore.Video.Media.DISPLAY_NAME + "=?";
        String[] args = new String[]{ fileName };

        Cursor cursor = getApplicationContext().getContentResolver()
                .query(collection, new String[]{MediaStore.Video.Media._ID}, selection, args, null);

        if (cursor != null && cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
            cursor.close();

            return Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + id);
        }

        if (cursor != null) cursor.close();
        return null;
    }

    private void copyFileUsingPath(File src, File dst) throws Exception {

        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dst)) {

            byte[] buffer = new byte[4096];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    private void copyFileUsingUri(Uri srcUri, File dst) throws Exception {

        ContentResolver resolver = getApplicationContext().getContentResolver();

        try (InputStream in = resolver.openInputStream(srcUri);
             OutputStream out = new FileOutputStream(dst)) {

            byte[] buffer = new byte[4096];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

}
