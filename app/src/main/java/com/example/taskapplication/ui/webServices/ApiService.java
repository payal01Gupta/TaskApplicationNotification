package com.example.taskapplication.ui.webServices;

import com.example.taskapplication.ui.model.JokeResponse;
import com.example.taskapplication.ui.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    // Example public API (fetch random joke)
    @GET("https://official-joke-api.appspot.com/random_joke")
    Call<JokeResponse> getJoke();

    @POST("posts")
    Call<User> createUser(@Body User user);
}
