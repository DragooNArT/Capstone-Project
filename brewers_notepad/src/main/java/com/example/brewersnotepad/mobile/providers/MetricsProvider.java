package com.example.brewersnotepad.mobile.providers;

import android.content.Context;
import android.widget.TextView;

import com.example.brewersnotepad.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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

    public double convertTempForStorage(String tempText) {
        double temp = Double.parseDouble(tempText.substring(0,tempText.length()-getTempPrefix().length()));
        if(!prov.getUseCelsius()) {
            return (temp - 32)*5/9;
        }
        return temp;
    }

    private double convertTempForDisplay(double temp) {
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
        return df.format(weight);
    }
    public double convertWeightTextForStorage(String weightText) {
        double weight = Double.parseDouble(weightText.substring(0,weightText.length()-getWeightPrefix().length()));
        if(!prov.getUseMetric()) {
            return weight/2.2;
        }
        return weight;
    }
    private double convertSmallWeightToGrams(double smallWeight) {
        if(!prov.getUseMetric()) {
            return smallWeight * 28.349;
        }
        return smallWeight;
    }

    private double convertSmallWeightToOz(double smallWeight) {
        if(!prov.getUseMetric()) {
            return smallWeight / 28.349;
        }
        return smallWeight;
    }

    public String convertTempToText(double mashTemp) {
        return df.format(convertTempForDisplay(mashTemp))+getTempPrefix();
    }

    public String convertWeightToText(double grainQuantity) {
        return convertWeightForStorage(grainQuantity)+getWeightPrefix();

    }

    public double convertSmallWeightToValue(String hopQuantity) {
        return convertSmallWeightToGrams(Double.parseDouble(hopQuantity.substring(0, hopQuantity.length() - getSmallWeightPrefix().length())));
    }

    public int convertMinsToValue(String time) {
        return Integer.parseInt(time.substring(0, time.length() - ctx.getString(R.string.time_in_minutes).length()));
    }

    public int convertDaysToValue(String time) {
        return Integer.parseInt(time.substring(0, time.length() - ctx.getString(R.string.time_in_days).length()));
    }

    public String convertSmallWeightToText(double hopQuantity) {
        return df.format(convertSmallWeightToOz(hopQuantity)) + getSmallWeightPrefix();
    }

    public String convertMinsToText(int mins) {
        return mins + ctx.getString(R.string.time_in_minutes);
    }
    public String convertDaysToText(int days) {
        return days + ctx.getString(R.string.time_in_days);
    }
}
