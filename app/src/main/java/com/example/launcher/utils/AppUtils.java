package com.example.launcher.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import com.example.launcher.models.AppInfo;
import com.example.launcher.ui.TempActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * AppUtils class manages all the tools required to manage various process in the app.
 * e.g.Pixel conversion to Density Pixels (dp) and vice-versa, collecting all installed apps in the device,
 * Resetting and setting Launcher settings.
 */

@SuppressLint("QueryPermissionsNeeded")
public class AppUtils {

    public static boolean traversed = false;

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static Intent getPackageIntent(String action, String category){
        Intent intent = new Intent(action);
        intent.addCategory(category);
        return intent;
    }
}
