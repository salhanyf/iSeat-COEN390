package com.example.finalproject.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "study_room_table")
public class Sensor {
    @PrimaryKey(autoGenerate = true)
    private int studyRoomID;
    @ColumnInfo(name = "study_room_name")
    private String studyRoomName;
    @ColumnInfo(name = "study_room_location")
    private String studyRoomLocation;
    @ColumnInfo(name = "study_room_status")
    private String studyRoomStatus;

    public Sensor(int id, String name, String location, String status) {
        this.studyRoomID = id;
        this.studyRoomName = name;
        this.studyRoomLocation = location;
        this.studyRoomStatus = status;
    }
}
