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
    private boolean isRecipeComplete(RecipeDataHolder recipe) {

        if(recipe.getRecipe_name()== null || recipe.getRecipe_name().isEmpty()) {

            return false;
        }
        if(recipe.getFermentation_phases().isEmpty()) {

            return false;
        }
        if(recipe.getRecipe_hops().isEmpty()) {

            return false;
        }
        if(recipe.getRecipe_grains().isEmpty()) {

            return false;
        }

        if(recipe.getRecipe_grains().isEmpty()) {

            return false;
        }
        if(recipe.getHopSteepDuration()<=0) {

            return false;
        }
        if(recipe.getMashTemp()<=0) {

            return false;
        }
        if(recipe.getMashDuration()<=0) {

            return false;
        }

        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item == activity.getDoneButton()) {
            RecipeDataHolder recipeInstance = activity.getRecipeInstance();
            if(isRecipeComplete(recipeInstance)) {
                //TODO add it to database, navigate to home
                Intent intent = new Intent(activity, MainActivity.class);
                //TODO put stuff
                activity.startActivity(intent);
            }
            return true;
        }
        return false;
    }
}
