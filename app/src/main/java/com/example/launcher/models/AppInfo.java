package com.example.launcher.models;

import android.graphics.drawable.Drawable;

/**
 * POJO class for AppInfo.
 */
public class AppInfo  {
    CharSequence label;
    CharSequence packageName;
    Drawable icon;

    public AppInfo(){}

    public CharSequence getLabel() {
        return label;
    }

    public void setLabel(CharSequence label) {
        this.label = label;
    }

    public CharSequence getPackageName() {
        return packageName;
    }

    public void setPackageName(CharSequence packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
