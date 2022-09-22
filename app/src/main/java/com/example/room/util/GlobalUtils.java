package com.example.room.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.room.db.NetworkApp;
import com.example.room.model.LocalApp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GlobalUtils {
    private static final String TAG = "GlobalUtils";
    private static List<LocalApp> localApps;
    private static List<NetworkApp> networkApps;
    private static List<String> introList;
    private static List<String> hideApps;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void initNetworkApps(Context context) {
        networkApps = AppUtils.getAllApp(context);
        if (networkApps.isEmpty()) {
            networkApps = AppUtils.getAppsAtNetwork(context);
        }
        networkApps = networkApps.stream()
                .filter(localApp -> !hideApps.contains(localApp.getLabel()))
                .collect(Collectors.toList());
        Log.e(TAG, "initNetworkApps: " + networkApps.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void initLocalApps(Context context) {
        localApps = AppUtils.getAppsAtLocal(context, true);
        localApps = localApps.stream().filter(localApp -> {
            return !hideApps.contains(localApp.getLabel());
        }).collect(Collectors.toList());
        Log.e(TAG, "initLocalApps: " + localApps.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void initIntroList(Context context) {
        introList = FileUtils.loadIntroList(context);
        Log.e(TAG, "initIntroList: " + introList.size());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void initHideApps(Context context) {
        hideApps = FileUtils.loadHideApps(context);
        Log.e(TAG, "initHideApps: " + hideApps.size());
    }

    public static void addHideApps(String name) {
        hideApps.add(name);
    }

    public static List<String> getHideApps() {
        return hideApps;
    }

    public static List<NetworkApp> getNetworkApps() {
        return networkApps;
    }

    public static List<LocalApp> getLocalApps() {
        return localApps;
    }

    public static List<String> getIntroList() {
        return introList;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void init(Context context) {
        initHideApps(context);
        initLocalApps(context);
        initNetworkApps(context);
        initIntroList(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void removeLocalApp(LocalApp localApp) {
        localApps = localApps.stream().filter(app -> !app.getLabel().equals(localApp.getLabel())).collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void removeNetworkApp(NetworkApp networkApp) {
        networkApps = networkApps.stream().filter(app -> !app.getLabel().equals(networkApp.getLabel())).collect(Collectors.toList());
    }
}
