package com.example.room.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.room.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changStatusIconCollor(true);
        binding.toolbar.back.setVisibility(View.GONE);
        binding.toolbar.editText.cancel.setVisibility(View.GONE);
        binding.toolbar.editText.edit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                startActivity(new Intent(binding.getRoot().getContext(), SearchActivity.class));
            }
            binding.toolbar.editText.edit.clearFocus();
        });
    }
}