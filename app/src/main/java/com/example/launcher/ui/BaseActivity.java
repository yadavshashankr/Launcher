package com.example.launcher.ui;

import static com.example.launcher.utils.AppUtils.getPackageIntent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.example.launcher.R;
import com.example.launcher.models.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the base activity.
 * Rather than adding views to container from every activity, we just pass the built Views in java files here.
 * Activities having extending this BaseActivity class will pass their custom built views by calling launch method.
 */
@SuppressLint("QueryPermissionsNeeded")
public class BaseActivity extends AppCompatActivity {
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        frameLayout = findViewById(R.id.container);
    }

    /**
     * Having a single function and single container for all the ui across the app.
     */
    protected void launch(RelativeLayout relativeLayout){
        frameLayout.addView(relativeLayout);
    }

    protected static List<AppInfo> getAllApps(Context context){

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

    public void resetPreferredLauncherAndOpenChooser() {
        if(!isMyAppLauncherDefault()){
            PackageManager packageManager = getPackageManager();
            packageManager.clearPackagePreferredActivities(getPackageName());
            ComponentName componentName = new ComponentName(this, TempActivity.class);
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            Intent selector = getPackageIntent(Intent.ACTION_MAIN, Intent.CATEGORY_HOME);
            selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(selector);

            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED, PackageManager.DONT_KILL_APP); // using DONT_KILL_APP with non system access devices, there it proves unpredictable. Could also cause apps to crash.
        }

    }

    private boolean isMyAppLauncherDefault() { // To check the default Launcher app.
        PackageManager localPackageManager = getPackageManager();
        Intent intent = getPackageIntent(Intent.ACTION_MAIN, Intent.CATEGORY_HOME);
        String str = localPackageManager.resolveActivity(intent,
                PackageManager.MATCH_DEFAULT_ONLY).activityInfo.packageName;
        return str.equals(getPackageName());
    }
}