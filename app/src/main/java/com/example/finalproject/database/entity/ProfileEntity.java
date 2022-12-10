/*
    This file was used in beginning for local database, but stop being used when we migrated to firebase.
*/
package com.example.finalproject.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_table")
public class ProfileEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "profile_type")
    public String profileType;

    @ColumnInfo(name = "profile_name")
    public String profileName;

    @ColumnInfo(name = "profile_password")
    public String profilePassword;

    public ProfileEntity(String profileType, String profileName, String profilePassword){
        this.profileType = profileType;
        this.profileName = profileName;
        this.profilePassword = profilePassword;
    }

    //getters and setters
    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }

    public String getProfileType() { return profileType; }

    public void setProfileType(String type) {
        this.profileType = type;
    }

    public String getName() { return profileName; }

    public void setName(String name) {
        this.profileName = name;
    }

    public String getPassword() { return profilePassword; }

    public void setPassword(String password) {
        this.profilePassword = password;
    }
}
