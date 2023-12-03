package com.example.androidexam.repositories;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.androidexam.R;
import com.example.androidexam.interfaces.UserDAO;
import com.example.androidexam.DB.AppDatabase;
import com.example.androidexam.models.User;
import com.example.androidexam.services.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private final Executor executor;
    private final ApiService apiService;
    private final UserDAO userDao;
    private final Context context;

    public UserRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "app-database").build();
        userDao = db.userDAO();
        this.context = context;
        apiService = createApiService();
        executor = Executors.newSingleThreadExecutor();
    }

    private ApiService createApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiService.class);
    }

    public LiveData<List<User>> getUsers() {
        refreshDataIfNeeded();
        return userDao.getAllUsers();
    }

    public void insertUsers(List<User> users) {
        executor.execute(() -> userDao.insertUsers(users));
    }


    private void refreshDataIfNeeded() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("users_ctx", Context.MODE_PRIVATE);
        int lastUserId = sharedPreferences.getInt("lastUserId", 0);

        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();
                    if (users != null && !users.isEmpty()) {
                        List<User> newUsers = new ArrayList<>();
                        for (User user : users) {
                            if (user.id > lastUserId) {
                                newUsers.add(user);
                            }
                        }

                        if (!newUsers.isEmpty()) {
                            insertUsers(newUsers);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("lastUserId", newUsers.get(newUsers.size() - 1).id);
                            editor.apply();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.e(context.getString(R.string.userrepository), context.getString(R.string.error_fetching_data), t);
            }
        });
    }

}

