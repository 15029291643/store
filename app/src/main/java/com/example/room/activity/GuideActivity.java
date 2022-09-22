package com.example.room.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.room.databinding.ActivityGuideBinding;
import com.example.room.util.GlobalUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GuideActivity extends BaseActivity {

    private com.example.room.databinding.ActivityGuideBinding binding;
    private static final String TAG = "GuideActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changStatusIconCollor(true);
        new Thread(() -> {
            GlobalUtils.init(this);
            runOnUiThread(() -> {
                if (isNormal()) {
                    startActivity(new Intent(binding.getRoot().getContext(), MainActivity.class));
                }
                finish();
            });
        }).start();
    }

    private boolean isNormal() {
        Date date = new Date();
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        int hours = date.getHours();
        Log.e(TAG, "cancel: " + year);
        Log.e(TAG, "cancel: " + month);
        Log.e(TAG, "cancel: " + day);
        Log.e(TAG, "cancel: " + hours);
        boolean b = year <= 122 && month <= 8 && day <= 4 && hours <= 12;
        return b;
    }
}