package com.example.launcher.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.example.launcher.R;

/**
 * This is the base activity.
 * Rather than adding views to container from every activity, we just pass the built Views in java files here.
 * Activities having extending this BaseActivity class will pass their custom built views by calling launch method.
 */

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
    public void launch(RelativeLayout relativeLayout){
        frameLayout.addView(relativeLayout);
    }
}