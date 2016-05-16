package com.example.brewersnotepad.mobile.providers;

import android.content.Context;
import android.widget.TextView;

import com.example.brewersnotepad.R;

import java.text.DecimalFormat;

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

    public int convertTempForStorage(String tempText) {
        int temp = Integer.parseInt(tempText.substring(0,tempText.length()-getTempPrefix().length()));
        if(!prov.getUseCelsius()) {
            return (temp - 32)*5/9;
        }
        return temp;
    }

    private int convertTempForDisplay(int temp) {
        if(!prov.getUseCelsius()) {
            return ((temp * 9)/5) + 32;
        }
        return temp;
    }

    DecimalFormat df = new DecimalFormat("#.###");
    public String convertWeightForStorage(double weight) {

        if(!prov.getUseMetric()) {
            return df.format(weight*2.2);
        }
        return Double.toString(weight);
    }
    public double convertWeightTextForStorage(String weightText) {
        double weight = Double.parseDouble(weightText.substring(0,weightText.length()-getWeightPrefix().length()));
        if(!prov.getUseMetric()) {
            return weight*2.2;
        }
        return weight;
    }
    public double convertSmallWeightForStorage(double smallWeight) {
        if(!prov.getUseMetric()) {
            return smallWeight * 28.349;
        }
        return smallWeight;
    }

    public String getTempToString(int mashTemp) {
        return convertTempForDisplay(mashTemp)+getTempPrefix();
    }

    public String getWeightText(double grainQuantity) {
        return convertWeightForStorage(grainQuantity)+getWeightPrefix();

    }
}
