package com.example.room.model;

import android.graphics.drawable.Drawable;

public class LocalApp {
    private String label;//应用名称
    private String packageName;//应用包名
    private Drawable icon;//应用icon

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}