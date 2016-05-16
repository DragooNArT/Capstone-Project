package com.example.brewersnotepad.mobile.listeners;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CompoundButton;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.fragments.MainPreferencesFragment;
import com.example.brewersnotepad.mobile.providers.SharedPreferencesProvider;

/**
 * Created by xnml on 16.5.2016 г..
 */
public class PreferencesChangedListener implements CompoundButton.OnCheckedChangeListener {

    private MainPreferencesFragment mFragment;
    private SharedPreferencesProvider mSharedPrefProvider;
    public PreferencesChangedListener(SharedPreferencesProvider sharedPrefProvider) {
        this.mSharedPrefProvider = sharedPrefProvider;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.useMetric) {
            mSharedPrefProvider.setUseMetric(isChecked);
        } else if (buttonView.getId() == R.id.useCelsius) {
            mSharedPrefProvider.setUseCelsius(isChecked);
        }
    }
}
