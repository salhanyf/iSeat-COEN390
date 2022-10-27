package com.example.finalproject.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.finalproject.database.entity.Sensor;

import java.util.List;

@Dao
public interface SensorDao {
    @Query("SELECT * FROM study_room_table")    // Select all the rows in table
    List<Sensor> getAll();

    @Query("SELECT * FROM study_room_table WHERE studyRoomID=:studyRoomID")   // Select a specific row in table
    Sensor findBtId(int studyRoomID);

    @Insert // Insert a row into table
    void insertAll(Sensor... sensors);

    @Delete // Delete a row from table
    void delete(Sensor sensor);
}
