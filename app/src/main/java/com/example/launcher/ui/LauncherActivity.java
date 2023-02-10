package com.example.launcher.ui;


import static com.example.launcher.utils.AppUtils.traversed;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.example.launcher.adapter.AppsDrawerAdapter;

/**
 * This is a simple Activity, whose work is to set the list of launcher apps,
 * creating a list by adding/modifying its ui parameters programmatically,
 * passing appropriate parameters to view and its elements
 * and finally passing the created View to the container of BaseActivity.
 *
 * This activity being our Launcher, also checks for the current launcher app, if it
 * is this app then it loads normally or else it asks for Launcher selection.
 */

public class LauncherActivity extends BaseActivity {
    private AppsDrawerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!traversed){
            traversed = true;
            resetPreferredLauncherAndOpenChooser();
        }

        setLauncherList();
        launch(initViews());
    }

    public RelativeLayout initViews() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RecyclerView recyclerView = new RecyclerView(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        relativeLayout.addView(recyclerView);
        return relativeLayout;
    }

    private void setLauncherList(){
        adapter = new AppsDrawerAdapter(this, getAllApps(getApplicationContext()));
    }

}