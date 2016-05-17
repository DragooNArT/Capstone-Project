package com.example.brewersnotepad.mobile.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.View;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.CreateRecipeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

/**
 * Created by DragooNArT-PC on 5/9/2016.
 */
public class MainActivityFabListener implements View.OnClickListener {
    private Activity activity;
    InterstitialAd mInterstitialAd;
    Random randomAd = new Random();
    public MainActivityFabListener(final Activity activity) {
        this.activity = activity;
        mInterstitialAd = new InterstitialAd(activity);
        mInterstitialAd.setAdUnitId(activity.getString(R.string.interstitial_ad_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(activity, CreateRecipeActivity.class);
                activity.startActivity(intent);
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("1FF17D52211B635F5E070358405A1213")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onClick(View view) {

        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Intent intent = new Intent(activity, CreateRecipeActivity.class);
            activity.startActivity(intent);
        }

        if(randomAd.nextBoolean()) {
            requestNewInterstitial();
        }
    }
}
