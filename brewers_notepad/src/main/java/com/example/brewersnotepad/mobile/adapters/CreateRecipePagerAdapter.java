package com.example.brewersnotepad.mobile.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.CreateRecipeActivity;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeExtrasFragment;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFragmentSecondary;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFramentMain;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import java.util.ArrayList;

/**
 * Created by xnml on 11.5.2016 г..
 */
public class CreateRecipePagerAdapter extends FragmentPagerAdapter implements CreateRecipeFramentMain.OnFragmentInteractionListener,CreateRecipeFragmentSecondary.OnFragmentInteractionListener {

    public CreateRecipePagerAdapter(FragmentManager fm) {
        super(fm);
    }
    private CreateRecipeFragmentSecondary mFragmentSecondary;
    private CreateRecipeFramentMain mFragmentMain;
    private CreateRecipeExtrasFragment mFragmentExtra;
    private CreateRecipeActivity activity;
    public CreateRecipePagerAdapter(FragmentManager supportFragmentManager, CreateRecipeActivity activity) {
        super(supportFragmentManager);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            if(mFragmentMain == null) {
                mFragmentMain = new CreateRecipeFramentMain();
                Bundle args = new Bundle();
                args.putBoolean(CreateRecipeActivity.NEW_RECIPE_KEY,activity.isNewRecipe());
                args.putParcelableArrayList(CreateRecipeFramentMain.GRAIN_PARCELABLE, (ArrayList<? extends Parcelable>) RecipeRuntimeManager.getCurrentRecipe().getRecipe_grains());
                mFragmentMain.setArguments(args);
            }
            return mFragmentMain;
            case 1:
                if(mFragmentSecondary == null) {
                    mFragmentSecondary =  new CreateRecipeFragmentSecondary();
                }
                return mFragmentSecondary;
            case 2:
                if(mFragmentExtra == null) {
                    mFragmentExtra = new CreateRecipeExtrasFragment();
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
                return activity.getString(R.string.create_tabs_main);
            case 1:
                return activity.getString(R.string.create_tabs_second);
            case 2:
                return activity.getString(R.string.create_tabs_third);
        }
        return null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
