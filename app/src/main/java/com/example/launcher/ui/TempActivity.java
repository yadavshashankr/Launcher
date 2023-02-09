package com.example.launcher.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class TempActivity extends AppCompatActivity {

    /**
     * This activity is used to hold the dialog for Launcher selection.
     * This activity's default state is disabled.
     * It is enabled for few moments until it shows the dialog and
     * switches to LauncherActivity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onResume(){
        super.onResume();
        startActivity(new Intent(this, LauncherActivity.class));
    }
}