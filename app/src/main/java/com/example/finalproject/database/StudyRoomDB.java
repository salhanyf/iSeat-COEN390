package com.example.finalproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.finalproject.database.dao.RoomDao;
import com.example.finalproject.database.entity.RoomEntity;

@Database(entities = {RoomEntity.class}, version = 1)
public abstract class StudyRoomDB extends RoomDatabase {

    private static volatile StudyRoomDB instance;
    private static final String DB_NAME = "study_room_db";

    protected StudyRoomDB() {}

    private static StudyRoomDB create (Context context) {   // Might crash if database too big
        return Room.databaseBuilder(context, StudyRoomDB.class, DB_NAME).allowMainThreadQueries().build();
    }

    public static synchronized StudyRoomDB getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public abstract RoomDao sensorDao();
}
