package com.example.brewersnotepad.mobile.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.brewersnotepad.mobile.activities.CreateRecipeActivity;

/**
 * Created by DragooNArT-PC on 5/9/2016.
 */
public class MainActivityFabListener implements View.OnClickListener {
    private Activity activity;
    public MainActivityFabListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, CreateRecipeActivity.class);
        //TODO put stuff
        activity.startActivity(intent);
    }
}
