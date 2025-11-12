package com.example.taskapplication.ui.webServices;

import com.example.taskapplication.ui.model.JokeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    // Example public API (fetch random joke)
    @GET("https://official-joke-api.appspot.com/random_joke")
    Call<JokeResponse> getJoke();
}
