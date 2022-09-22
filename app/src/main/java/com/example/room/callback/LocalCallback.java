package com.example.room.callback;

import com.example.room.model.LocalApp;

import java.util.List;

public interface LocalCallback {
    void onNext(List<LocalApp> localApps);
}
