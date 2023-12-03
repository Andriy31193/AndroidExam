package com.example.androidexam.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidexam.interfaces.UserDAO;
import com.example.androidexam.models.User;


@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}


