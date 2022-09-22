package com.example.room.callback;

import com.example.room.db.NetworkApp;

import java.util.List;

public interface NetworkCallback {
    void onNext(List<NetworkApp> apps);
}
