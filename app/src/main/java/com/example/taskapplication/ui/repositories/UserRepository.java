package com.example.taskapplication.ui.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.taskapplication.ui.model.MessageResponse;
import com.example.taskapplication.ui.model.User;
import com.example.taskapplication.ui.model.UserNew;
import com.example.taskapplication.ui.webServices.ApiService;
import com.example.taskapplication.ui.webServices.RetrofitClient;

import java.util.List;
import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository {
    private ApiService apiService;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public UserRepository() {
        apiService = RetrofitClient.getApi();
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<List<UserNew>> fetchUsers() {
        MutableLiveData<List<UserNew>> usersLiveData = new MutableLiveData<>();
        loading.setValue(true);
        apiService.getUsers().enqueue(new Callback<List<UserNew>>() {
            @Override
            public void onResponse(Call<List<UserNew>> call, Response<List<UserNew>> response) {
                loading.setValue(false);
                usersLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<UserNew>> call, Throwable t) {
                loading.setValue(false);
                usersLiveData.setValue(null);
            }
        });
        return usersLiveData;
    }

    public LiveData<String> fetchMessage() {
        MutableLiveData<String> messageLiveData = new MutableLiveData<>();
        loading.setValue(true);
        apiService.getMessage().enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                loading.setValue(false);
                messageLiveData.setValue(response.body().getMessage());
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                loading.setValue(false);
                messageLiveData.setValue("Error occurred");
            }
        });
        return messageLiveData;
    }
}
