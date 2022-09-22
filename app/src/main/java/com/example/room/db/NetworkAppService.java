package com.example.room.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NetworkAppService {
    @Insert
    void add(NetworkApp app);

    @Query("SELECT * FROM NetworkApp")
    List<NetworkApp> getAll();
}
