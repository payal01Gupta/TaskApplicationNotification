package com.example.taskapplication.ui.viewModel;

import android.media.tv.TsRequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskapplication.ui.model.MessageResponse;
import com.example.taskapplication.ui.model.UserNew;
import com.example.taskapplication.ui.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {

    UserRepository repository = new UserRepository();

    public LiveData<List<UserNew>> getUser(){
        return repository.fetchUsers();
    }
    public LiveData<String> getText(){
        return repository.fetchMessage();
    }

    public LiveData<Boolean> _isLoading(){
        return repository.getLoading();
    }
}
