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

    public Sensor() {
        this.studyRoomID = 0;
        this.studyRoomName = "null";
        this.studyRoomLocation = "null";
        this.studyRoomStatus = "null";
    }

    public void setStudyRoomID(int studyRoomID) {
        this.studyRoomID = studyRoomID;
    }

    public int getStudyRoomID() {
        return studyRoomID;
    }

    public void setStudyRoomLocation(String studyRoomLocation) {
        this.studyRoomLocation = studyRoomLocation;
    }

    public String getStudyRoomLocation() {
        return studyRoomLocation;
    }

    public void setStudyRoomName(String studyRoomName) {
        this.studyRoomName = studyRoomName;
    }

    public String getStudyRoomName() {
        return studyRoomName;
    }

    public void setStudyRoomStatus(String studyRoomStatus) {
        this.studyRoomStatus = studyRoomStatus;
    }

    public String getStudyRoomStatus() {
        return studyRoomStatus;
    }
}
