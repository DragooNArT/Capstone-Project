package com.example.brewersnotepad.mobile.listeners;

import android.content.Intent;
import android.view.MenuItem;

import com.example.brewersnotepad.mobile.activities.CreateRecipeActivity;
import com.example.brewersnotepad.mobile.activities.MainActivity;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class CreateRecipeListener implements MenuItem.OnMenuItemClickListener {

    private CreateRecipeActivity activity;

    public CreateRecipeListener(CreateRecipeActivity createRecipeActivity) {
        this.activity = createRecipeActivity;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item == activity.getDoneButton()) {
            RecipeDataHolder recipeInstance = activity.getRecipeInstance();
            if(recipeInstance.isRecipeComplete()) {
                //TODO add it to database, navigate to home
                Intent intent = new Intent(activity, MainActivity.class);
                //TODO put stuff
                activity.startActivity(intent);
            } else {
                //TODO prompt to save as draft or discard
            }
            return true;
        }
        return false;
    }
}
