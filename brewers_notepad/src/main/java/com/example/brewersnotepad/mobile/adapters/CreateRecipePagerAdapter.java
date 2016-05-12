package com.example.brewersnotepad.mobile.adapters;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.brewersnotepad.mobile.fragments.CreateRecipeFragmentSecondary;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFramentMain;

/**
 * Created by xnml on 11.5.2016 г..
 */
public class CreateRecipePagerAdapter extends FragmentPagerAdapter implements CreateRecipeFramentMain.OnFragmentInteractionListener,CreateRecipeFragmentSecondary.OnFragmentInteractionListener {

    public CreateRecipePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CreateRecipeFramentMain();
            case 1:
                return new CreateRecipeFragmentSecondary();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Main";
            case 1:
                return "Fermentation + Extras";
        }
        return null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}