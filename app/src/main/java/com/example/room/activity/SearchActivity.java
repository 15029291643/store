package com.example.room.activity;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.room.R;
import com.example.room.adapter.SearchAdapter;
import com.example.room.databinding.ActivitySearchBinding;
import com.example.room.util.GlobalUtils;
import com.example.room.util.StringUtils;


import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchActivity extends BaseActivity {

    private ActivitySearchBinding binding;
    private SearchAdapter adapter;
    private static final String TAG = "SearchActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        changStatusIconCollor(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.toolbar.editText.cancel.setVisibility(View.GONE);
        binding.toolbar.editText.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setAdapter();
            }
        });
        binding.toolbar.editText.cancel.setOnClickListener(v -> {
            binding.toolbar.editText.edit.setText("");
            setAdapter();
        });
        binding.toolbar.back.setOnClickListener(v -> finish());
        setAdapter();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAdapter() {
        String edit = binding.toolbar.editText.edit.getText().toString();
        if (edit.length() == 0) {
            binding.toolbar.editText.cancel.setVisibility(View.GONE);
            binding.recyclerView.setBackground(getResources().getDrawable(R.drawable.bg_search));
            adapter.setNetworkApps(new ArrayList<>());
            adapter.setLocalApps(new ArrayList<>());
        } else {
            if (binding.toolbar.editText.cancel.getVisibility() == View.GONE) {
                binding.toolbar.editText.cancel.setVisibility(View.VISIBLE);
            }
            binding.recyclerView.setBackgroundColor(Color.WHITE);
            adapter.setLocalApps(GlobalUtils.getLocalApps().stream().filter(app -> {
                try {
                    return StringUtils.toFirstChar(app.getLabel().replace("：", "")).contains(StringUtils.toFirstChar(edit));
                } catch (Exception e) {
                    return false;
                }
            }).collect(Collectors.toList()));
            adapter.setNetworkApps(GlobalUtils.getNetworkApps().stream().filter(app -> {
                try {
                    return StringUtils.toFirstChar(app.getLabel()).contains(StringUtils.toFirstChar(edit));
                } catch (Exception e) {
                    return false;
                }
            }).collect(Collectors.toList()));
        }
    }
}