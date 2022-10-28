package com.example.finalproject.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.finalproject.database.entity.RoomEntity;

import java.util.List;

@Dao
public interface RoomDao {
    @Query("SELECT * FROM study_room_table")    // Select all the rows in table
    List<RoomEntity> getAll();

    @Query("SELECT * FROM study_room_table WHERE studyRoomID=:studyRoomID")   // Select a specific row in table
    RoomEntity findById(int studyRoomID);

    @Insert // Insert a row into table
    void insertAll(RoomEntity... roomEntities);

    @Delete // Delete a row from table
    void delete(RoomEntity roomEntity);
}
