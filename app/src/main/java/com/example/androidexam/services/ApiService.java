package com.example.androidexam.services;

import com.example.androidexam.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/users")
    Call<List<User>> getUsers();
}

