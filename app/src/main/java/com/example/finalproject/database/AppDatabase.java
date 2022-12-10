/*
    This file was used in beginning for local database, but stop being used when we migrated to firebase.
*/
package com.example.finalproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finalproject.database.dao.ProfileDao;
import com.example.finalproject.database.dao.RoomDao;
import com.example.finalproject.database.entity.ProfileEntity;
import com.example.finalproject.database.entity.RoomEntity;

@Database(entities = { ProfileEntity.class, RoomEntity.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance;
    private static final String DB_NAME = "app_database";

    protected AppDatabase() {}

    private static AppDatabase create (Context context) {   // Might crash if database too big
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public abstract RoomDao roomDao();
    public abstract ProfileDao profileDao();
}
