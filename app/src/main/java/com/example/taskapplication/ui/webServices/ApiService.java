package com.example.taskapplication.ui.webServices;

import com.example.taskapplication.ui.model.JokeResponse;
import com.example.taskapplication.ui.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    // Example public API (fetch random joke)
    @GET("https://official-joke-api.appspot.com/random_joke")
    Call<JokeResponse> getJoke();

    @POST("posts")
    Call<User> createUser(@Body User user);

    @GET("player_api.php")
    Call<List<VodStreamsCallback>>allVODStreams(
            @Header("Content-Type") String contentType,
            @Query("username") String username,
            @Query("password") String password,
            @Query("action") String getVodStreams);

}
