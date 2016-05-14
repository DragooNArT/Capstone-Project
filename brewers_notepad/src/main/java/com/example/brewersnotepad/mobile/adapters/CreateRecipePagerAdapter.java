package com.example.brewersnotepad.mobile.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.CreateRecipeActivity;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeExtrasFragment;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFragmentSecondary;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFramentMain;

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
    private Context ctx;
    public CreateRecipePagerAdapter(FragmentManager supportFragmentManager, Context ctx) {
        super(supportFragmentManager);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            if(mFragmentMain == null) {
                mFragmentMain = new CreateRecipeFramentMain();
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
                return ctx.getString(R.string.create_tabs_main);
            case 1:
                return ctx.getString(R.string.create_tabs_second);
            case 2:
                return ctx.getString(R.string.create_tabs_third);
        }
        return null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
