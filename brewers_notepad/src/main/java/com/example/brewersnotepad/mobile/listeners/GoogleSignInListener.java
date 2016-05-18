package com.example.brewersnotepad.mobile.listeners;

import android.accounts.AccountManager;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.brewersnotepad.mobile.activities.MainActivity;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

/**
 * Created by xnml on 18.5.2016 г..
 */
public class GoogleSignInListener implements Button.OnClickListener {
    private MainActivity mainActivity;
    public GoogleSignInListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    @Override
    public void onClick(View v) {
        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null,
                new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, true, null, null, null, null);
        mainActivity.createGoogleApiClient_and_connect(true);
//        mainActivity.getGoogleApiClient().connect();
    }




}
