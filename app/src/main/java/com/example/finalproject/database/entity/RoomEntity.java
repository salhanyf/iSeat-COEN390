/*
    This file was used in beginning for local database, but stop being used when we migrated to firebase.
*/
package com.example.finalproject.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "study_room_table")
public class RoomEntity {
    @PrimaryKey(autoGenerate = true)
    public int studyRoomID;
    @ColumnInfo(name = "study_room_name")
    public String studyRoomName;
    @ColumnInfo(name = "study_room_location")
    public String studyRoomLocation;
    @ColumnInfo(name = "study_room_status")
    public String studyRoomStatus;
    @ColumnInfo(name = "study_room_seats")
    public String studyRoomSeats;

    public RoomEntity(int id, String name, String location, String status, String seats) {
        this.studyRoomID = id;
        this.studyRoomName = name;
        this.studyRoomLocation = location;
        this.studyRoomStatus = status;
        this.studyRoomSeats = seats;
    }
    public RoomEntity() {
        this.studyRoomID = 0;
        this.studyRoomName = "null";
        this.studyRoomLocation = "null";
        this.studyRoomStatus = "null";
        this.studyRoomSeats = "null";
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

    public void setStudyRoomSeats(String studyRoomSeats) {
        this.studyRoomSeats = studyRoomSeats;
    }

    public String getStudyRoomSeats() {
        return studyRoomSeats;
    }
}
