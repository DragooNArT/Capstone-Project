package com.example.brewersnotepad.mobile.listeners;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.CreateRecipeActivity;
import com.example.brewersnotepad.mobile.activities.MainActivity;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;

import org.w3c.dom.Text;

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
            fillRecipe(recipeInstance);
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

    void fillRecipe(RecipeDataHolder recipeInstance) {
        fillFromMainFragment(recipeInstance);
        fillFromSecondaryFragment(recipeInstance);
    }

    private void fillFromSecondaryFragment(RecipeDataHolder recipeInstance) {
        TextView hop_duration = (TextView) activity.findViewById(R.id.hopPhaseDurationInput);
        String hopDurVal = hop_duration.getText().toString();
        if (hopDurVal != null && !hopDurVal.isEmpty()) {
            try {
                recipeInstance.setHopSteepDuration(Integer.parseInt(hopDurVal));
            } catch (NumberFormatException e) {
                //TODO toast invalid value
            }
        }
    }

    private void fillFromMainFragment(RecipeDataHolder recipeInstance) {
        TextView recipe_name = (TextView)activity.findViewById(R.id.inputRecipeName);
        String recipeNameVal = recipe_name.getText().toString();
        recipeInstance.setRecipe_name(recipeNameVal);

        Spinner recipe_type_spinner = (Spinner)activity.findViewById(R.id.recipe_type_spinner);
        String recipe_type = ((TextView)recipe_type_spinner.getSelectedView()).getText().toString();
        recipeInstance.setRecipe_type(recipe_type);

        TextView mash_duration = (TextView)activity.findViewById(R.id.inputMashDuration);
        String mashDurValue = mash_duration.getText().toString();
        if(mashDurValue!=null && !mashDurValue.isEmpty()) {
            try {
                recipeInstance.setMashDuration(Integer.parseInt(mashDurValue));
            } catch(NumberFormatException e) {
                //TODO toast invalid value
            }
        }

        TextView mash_temp_input = (TextView)activity.findViewById(R.id.mashTempInput);
        String mashTempVal = mash_temp_input.getText().toString();
        if(mashTempVal!=null && !mashTempVal.isEmpty()) {
            try {
                recipeInstance.setMashTemp(Integer.parseInt(mashTempVal));
            } catch(NumberFormatException e) {
                //TODO toast invalid value
            }
        }
    }
}
