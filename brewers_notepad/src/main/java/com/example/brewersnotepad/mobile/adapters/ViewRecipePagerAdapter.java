package com.example.brewersnotepad.mobile.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.brewersnotepad.mobile.activities.ViewRecipeActivity;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeExtrasFragment;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFragmentSecondary;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFramentMain;
import com.example.brewersnotepad.mobile.fragments.ViewRecipeMain;
import com.example.brewersnotepad.mobile.fragments.ViewRecipeSecondary;

/**
 * Created by xnml on 11.5.2016 г..
 */
public class ViewRecipePagerAdapter extends FragmentPagerAdapter {
    private ViewRecipeMain mFragmentMain;
    private ViewRecipeSecondary mFragmentSecondary;
    private CreateRecipeExtrasFragment mFragmentExtra;
    private String recipeId;
    public ViewRecipePagerAdapter(FragmentManager fm,String recipeId) {
        super(fm);
        this.recipeId = recipeId;
    }

    private void addRecipeNameToArgs(Fragment fragment) {
        Bundle arguments = new Bundle();
        arguments.putString(ViewRecipeActivity.RECIPE_ID_EXTRA,recipeId);
        fragment.setArguments(arguments);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(mFragmentMain == null) {
                    mFragmentMain = new ViewRecipeMain();
                    addRecipeNameToArgs(mFragmentMain);
                }
                return mFragmentMain;
            case 1:
                if(mFragmentSecondary == null) {
                    mFragmentSecondary =  new ViewRecipeSecondary();
                    addRecipeNameToArgs(mFragmentSecondary);
                }
                return mFragmentSecondary;
            case 2:
                if(mFragmentExtra == null) {
                    mFragmentExtra = new CreateRecipeExtrasFragment();
                    addRecipeNameToArgs(mFragmentExtra);
                }
                return mFragmentExtra;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Main & Grain";
            case 1:
                return "Hops And Fermentation";
            case 2:
                return "Extras";
        }
        return null;
    }
}
