package com.example.finalproject.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject.database.entity.ProfileEntity;
import com.example.finalproject.database.entity.RoomEntity;

import java.util.List;

@Dao
public interface ProfileDao {

    @Insert
    void insert(ProfileEntity profile);

    @Insert
    void insertAll(ProfileEntity... profiles);

    @Delete
    void deleteProfile(ProfileEntity profile);

    @Delete
    void deleteAll(ProfileEntity... profiles);

    @Query("UPDATE profile_table SET profile_password = :newPassword WHERE profile_name = :profileName")
    void updatePassword(String profileName, String newPassword);

    @Query("SELECT * FROM profile_table")
    List<ProfileEntity> getAll();

    @Query("SELECT * FROM profile_table WHERE profile_name LIKE :profileName LIMIT 1")
    ProfileEntity findByProfileName(String profileName);
}
