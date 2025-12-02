// JokeViewModel.java
package com.example.taskapplication.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskapplication.ui.model.JokeResponse;
import com.example.taskapplication.ui.webServices.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeViewModel extends ViewModel {

    private final MutableLiveData<String> jokeText = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final ApiService apiService;

    public JokeViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://official-joke-api.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<String> getJokeText() {
        return jokeText;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetchJoke() {
        isLoading.setValue(true); // start loading

        apiService.getJoke().enqueue(new Callback<JokeResponse>() {
            @Override
            public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
                isLoading.setValue(false); // stop loading
                if (response.isSuccessful() && response.body() != null) {
                    String joke = response.body().setup + "\n" + response.body().punchline;
                    jokeText.setValue(joke);
                } else {
                    jokeText.setValue("Error: response failed!");
                }
            }

            @Override
            public void onFailure(Call<JokeResponse> call, Throwable t) {
                isLoading.setValue(false); // stop loading
                jokeText.setValue("Failed: " + t.getMessage());
            }
        });
    }
}
