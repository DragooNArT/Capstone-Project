package com.example.brewersnotepad.mobile.providers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xnml on 16.5.2016 г..
 */
public class SharedPreferencesProvider {
    SharedPreferences preferences;
    public static final String SHARED_PREFS_ID = "com.example.brewersnotepad.shared.prefs";

    public static final String METRIC_PREF = "METRIC_PREFERENCE_KEY";

    public static final String CELSIUS_PREF = "CELSIUS_PREFERENCE_KEY";

    public SharedPreferencesProvider(Context ctx) {
        preferences = ctx.getSharedPreferences(SHARED_PREFS_ID,Context.MODE_PRIVATE);
    }


    public boolean getUseMetric() {
        return preferences.getBoolean(METRIC_PREF,true);
    }

    public boolean getUseCelsius() {
        return preferences.getBoolean(CELSIUS_PREF,true);
    }

    public void setUseMetric(boolean useMetric) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(METRIC_PREF,useMetric);
        editor.commit();
    }

    public void setUseCelsius(boolean useCelsius) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CELSIUS_PREF,useCelsius);
        editor.commit();
    }
}


