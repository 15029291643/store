package com.example.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 3, entities = NetworkApp.class, exportSchema = false)
public abstract class NetworkAppDatabase extends RoomDatabase {
    private static volatile NetworkAppDatabase instance = null;
    public abstract NetworkAppService getNetworkAppService();

    public static NetworkAppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NetworkAppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, NetworkAppDatabase.class, "app.dp")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
