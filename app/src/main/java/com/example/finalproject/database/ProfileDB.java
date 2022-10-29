package com.example.finalproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finalproject.database.dao.ProfileDao;
import com.example.finalproject.database.entity.ProfileEntity;

@Database(entities = {ProfileEntity.class}, version = 1, exportSchema = false)
public abstract class ProfileDB extends RoomDatabase {

    public abstract ProfileDao profileDao();
    private static volatile ProfileDB instance;
    private static final String DB_NAME = "profile_db";

    public synchronized static ProfileDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ProfileDB.class, DB_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }
}
