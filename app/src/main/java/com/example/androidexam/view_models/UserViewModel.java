package com.example.androidexam.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidexam.models.User;
import com.example.androidexam.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        UserRepository userRepository = new UserRepository(application);
        users = userRepository.getUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}

