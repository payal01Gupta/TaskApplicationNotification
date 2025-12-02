package com.example.taskapplication.miscellanious;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    public static Retrofit retrofitObject(Context context) {
        try {
            if (context != null) {
                String serverUrl = AppConst.SERVER_URL;


                OkHttpClient client = new OkHttpClient

                        .Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .followRedirects(true).build();

                Retrofit retrofit = new Retrofit.Builder().baseUrl(serverUrl) //serverUrl

                        .client(client)


                        .addConverterFactory(GsonConverterFactory.create())

                        .build();
                return retrofit;
            }
            return null;
        } catch (NullPointerException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
