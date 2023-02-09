package com.example.launcher.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import com.example.launcher.constants.AppInfo;
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
    public static List<AppInfo> getAllApps(Context context){

        PackageManager pManager = context.getPackageManager();
        ArrayList<AppInfo> appsList = new ArrayList<AppInfo>();

        final PackageManager pm = context.getPackageManager();
         List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo ri : packages) {
            if (pm.getLaunchIntentForPackage(ri.packageName) != null){ // To separate out System packages which are generally not visible to user.
                AppInfo app = new AppInfo();
                app.setLabel(ri.loadLabel(pManager));
                app.setPackageName(ri.packageName);
                app.setIcon(ri.loadIcon(pManager));
                appsList.add(app);
            }
        }
        return appsList;
    }

    public static void resetPreferredLauncherAndOpenChooser(Context context) {  // Resetting of Launcher app in case if it is not this.
        PackageManager packageManager = context.getPackageManager();
        packageManager.clearPackagePreferredActivities(context.getPackageName());
        ComponentName componentName = new ComponentName(context, TempActivity.class);
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Intent selector = getPackageIntent(context);
        selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(selector);

        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED, PackageManager.DONT_KILL_APP); // using DONT_KILL_APP with non system access devices, there it proves unpredictable. Could also cause apps to crash.
    }

    public static boolean isMyAppLauncherDefault(Context context) { // To check the default Launcher app.
        PackageManager localPackageManager = context.getPackageManager();
        Intent intent = getPackageIntent(context);
        String str = localPackageManager.resolveActivity(intent,
                PackageManager.MATCH_DEFAULT_ONLY).activityInfo.packageName;
        return str.equals(context.getPackageName());
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static Intent getPackageIntent(Context context){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        return intent;
    }
}
