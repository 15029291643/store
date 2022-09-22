package com.example.room.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FileUtils {
    private static final String TAG = "FileUtils";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String> loadHideApps(Context context) {
        try {
            FileInputStream input = context.openFileInput(ConstantUtils.HIDE_APP);
            int available = input.available();
            byte[] bytes = new byte[available];
            input.read(bytes);
            List<String> list = Arrays.stream(new String(bytes).split(", ")).collect(Collectors.toList());
            input.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void saveHideApps(Context context, List<String> list) {
        try {
            FileOutputStream output = context.openFileOutput(ConstantUtils.HIDE_APP, Context.MODE_PRIVATE);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    builder.append(list.get(i));
                } else {
                    builder.append(", ").append(list.get(i));
                }
            }
            byte[] bytes = builder.toString().getBytes();
            output.write(bytes);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "saveHideApps: " + list.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String> loadIntroList(Context context) {
        try {
            InputStream input = context.getAssets().open("info.txt");
            int available = input.available();
            byte[] bytes = new byte[available];
            input.read(bytes);
            List<String> introList = Arrays.stream(new String(bytes).split("\n")).map(s -> s.split("、")[1].replace("。", "")).collect(Collectors.toList());
            Log.e(TAG, "initIntroList: " + introList.size());
            input.close();
            return introList;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
