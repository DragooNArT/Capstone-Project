package com.example.brewersnotepad.mobile.listeners;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.CreateRecipeActivity;
import com.example.brewersnotepad.mobile.activities.MainActivity;
import com.example.brewersnotepad.mobile.adapters.CreateRecipePagerAdapter;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import org.w3c.dom.Text;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class CreateRecipeListener implements MenuItem.OnMenuItemClickListener {

    private CreateRecipeActivity activity;
    private MetricsProvider metricsProvider;
    private boolean newRecipe;
    public CreateRecipeListener(CreateRecipeActivity createRecipeActivity, boolean newRecipe) {
        this.activity = createRecipeActivity;
        this.newRecipe = newRecipe;
        this.metricsProvider = new MetricsProvider(activity);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item == activity.getDoneButton()) {
            RecipeDataHolder recipeInstance = activity.getRecipeInstance();
            fillRecipe(recipeInstance);
            if(recipeInstance.getRecipe_name()!=null && !recipeInstance.getRecipe_name().isEmpty()) {
                if (recipeInstance.isRecipeComplete()) {
                    finalizeRecipe(recipeInstance);
                } else {
                    promptSaveDraft(recipeInstance);
                }
            } else {
                Toast.makeText(activity, "Can't save recipe with no name!", Toast.LENGTH_LONG).show();
                focusOnRecipeName();
            }
            return true;
        }
        return false;
    }

    private void finalizeRecipe(RecipeDataHolder recipeInstance) {
        if(newRecipe && RecipeRuntimeManager.getRecipe(recipeInstance.getRecipe_name()) != null) {
            Toast.makeText(activity, "Recipe name \""+recipeInstance.getRecipe_name()+"\" already exists!", Toast.LENGTH_LONG).show();
            focusOnRecipeName();
        } else {
            activity.fillData(recipeInstance,newRecipe);
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra(MainActivity.EXTRA_REFRESH, true);
            activity.startActivity(intent);
        }
    }

    private void focusOnRecipeName() {

        ViewPager pager = (ViewPager)activity.findViewById(R.id.create_recpie_pager);
        if(pager.getCurrentItem() != 0) {
            pager.setCurrentItem(0);
        }
        View inputRecipe = activity.findViewById(R.id.inputRecipeName);
        if(inputRecipe != null) {
            inputRecipe.requestFocus();
        }
    }

    private void promptSaveDraft(final RecipeDataHolder recipeInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.prompt_save_draft_title));
        builder.setPositiveButton(activity.getString(R.string.save_draft_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finalizeRecipe(recipeInstance);
            }
        });
        builder.setNegativeButton(activity.getString(R.string.cancel_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
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
                int temp = metricsProvider.convertTempForStorage(Integer.parseInt(mashTempVal));
                recipeInstance.setMashTemp(temp);
            } catch(NumberFormatException e) {
                //TODO toast invalid value
            }
        }
    }
}
