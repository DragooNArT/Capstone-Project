package com.example.brewersnotepad.mobile.providers;

import android.content.Context;

import com.example.brewersnotepad.R;

/**
 * Created by xnml on 16.5.2016 г..
 */
public class MetricsProvider {

    SharedPreferencesProvider prov;
    private Context ctx;
    public MetricsProvider(Context ctx) {
        this.ctx = ctx;
        prov = new SharedPreferencesProvider(ctx);
    }


    public String getWeightPrefix() {
        if(prov.getUseMetric()) {
            return ctx.getString(R.string.metric_weight);
        }
        return ctx.getString(R.string.nonMetric_weight);
    }

    public String getTempPrefix() {
        if(prov.getUseCelsius()) {
            return ctx.getString(R.string.celsius_prefix);
        }
        return ctx.getString(R.string.farenheight_prefix);
    }

    public String getSmallWeightPrefix() {
        if(prov.getUseMetric()) {
            return ctx.getString(R.string.gram_prefix);
        }
        return ctx.getString(R.string.ounce_prefix);
    }

    public int convertTempForStorage(int temp) {
        if(!prov.getUseCelsius()) {
            return ((temp - 32)*5)/9;
        }
        return temp;
    }

    public double convertWeightForStorage(double weight) {
        if(!prov.getUseMetric()) {
            return weight/2.2;
        }
        return weight;
    }
    public double convertSmallWeightForStorage(double smallWeight) {
        if(!prov.getUseMetric()) {
            return smallWeight * 28.349;
        }
        return smallWeight;
    }
}
