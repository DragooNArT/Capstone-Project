package com.example.brewersnotepad.mobile.activities;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.fragments.MainAboutFragment;
import com.example.brewersnotepad.mobile.fragments.MainPreferencesFragment;
import com.example.brewersnotepad.mobile.fragments.MainRecipeListFragment;
import com.example.brewersnotepad.mobile.listeners.GoogleSignInListener;
import com.example.brewersnotepad.mobile.listeners.MainActivityNavigationListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.drive.Drive;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, MainRecipeListFragment.OnFragmentInteractionListener, MainAboutFragment.OnFragmentInteractionListener, MainPreferencesFragment.OnFragmentInteractionListener {


    public static final String FRAGMENT_STATE = "FRAGMENT_ID";
    private static final String STORAGE_USERNAME_KEY = "UserPrefKey";
    private static final int GET_USER_INFO_KEY = 12412;

    private MainActivityNavigationListener navListener;

    /**
     * Request code for auto Google Play Services error resolution.
     */
    public static final int REQUEST_CODE_RESOLUTION = 1;

    /**
     * Google API client.
     */
    private GoogleApiClient mGoogleApiClient;

    private GoogleSignInListener mGoogleListener;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String accountName = getPreferences(MODE_PRIVATE).getString(STORAGE_USERNAME_KEY, null);
//        if (mGoogleApiClient == null && accountName != null) {
//            createGoogleApiClient_and_connect(false);
//        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mGoogleListener = new GoogleSignInListener(this);
        navListener = new MainActivityNavigationListener(this);
        navigationView.setNavigationItemSelectedListener(navListener);
        configNavHeader(null);
        boolean recoveredFragment = false;

        if (savedInstanceState != null) {
            int menuItemId = savedInstanceState.getInt(FRAGMENT_STATE);
            if (menuItemId > 0) {
                navListener.onNavigationItemSelected(menuItemId);
                recoveredFragment = true;
            }
        }

        if (!recoveredFragment) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragmentContainer, new MainRecipeListFragment());

            transaction.commit();
        }


    }

    public void createGoogleApiClient_and_connect(boolean shouldConnect) {
        try {
            if (!(mGoogleApiClient != null && mGoogleApiClient.isConnected())&& shouldConnect) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
//                .requestIdToken("AIzaSyA8uW-O5Q45Yg7abcH94Uw0FSduwA8LEX0")
                        .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
//                .setAccountName(accountName)
                        .build();

                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .addScope(Drive.SCOPE_APPFOLDER) // required for App Folder sample
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .build();
                mGoogleApiClient.connect();
            }
        } catch(Exception e) {
            Log.e(getClass().getName(),"Failed google services connectivity",e);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        outState.putInt(FRAGMENT_STATE, navListener.getLastSelectedItemId());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Handles resolution callbacks.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data != null) {
                if (requestCode == REQUEST_CODE_RESOLUTION) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences.Editor prefEdit = getPreferences(MODE_PRIVATE).edit();
                        prefEdit.putString(STORAGE_USERNAME_KEY, accountName);
                        createGoogleApiClient_and_connect(true);
                        prefEdit.commit();
                    }
                } else if (requestCode == GET_USER_INFO_KEY) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    configNavHeader(result.getSignInAccount());
                }
            }
        } catch(Exception e) {
            Log.e(getClass().getName(),"Failed google services connectivity",e);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private View sideNavHeader;

    private void configNavHeader(GoogleSignInAccount userInfo) {
        if (sideNavHeader != null)
            navigationView.removeHeaderView(sideNavHeader);

        sideNavHeader =
                getLayoutInflater().inflate(R.layout.nav_header_main, navigationView, false);
//                navigationView.inflateHeaderView(R.layout.nav_header_main);


        SignInButton signButton = (SignInButton) sideNavHeader.findViewById(R.id.sign_in_button);
        TextView username = (TextView) sideNavHeader.findViewById(R.id.googleUsername);
        TextView mail = (TextView) sideNavHeader.findViewById(R.id.googleMail);
        ImageView userImage = (ImageView) sideNavHeader.findViewById(R.id.googleImage);
        signButton.setOnClickListener(mGoogleListener);
        if (userInfo != null) {
            String personName = userInfo.getDisplayName();
            username.setText(personName);
            username.setVisibility(View.VISIBLE);
            mail.setText(userInfo.getEmail());
            mail.setVisibility(View.VISIBLE);
            Picasso.with(this).load(userInfo.getPhotoUrl()).into(userImage);
            userImage.setVisibility(View.VISIBLE);
            signButton.setVisibility(View.INVISIBLE);
        } else {
            mail.setVisibility(View.INVISIBLE);
            userImage.setVisibility(View.INVISIBLE);
            signButton.setVisibility(View.VISIBLE);
            mail.setVisibility(View.INVISIBLE);
            username.setVisibility(View.INVISIBLE);
        }
        navigationView.addHeaderView(sideNavHeader);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.i(getClass().getName(), "GoogleApiClient connected");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GET_USER_INFO_KEY);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(getClass().getName(), "GoogleApiClient connection suspended");
    }

    /**
     * Getter for the {@code GoogleApiClient}.
     */
    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(getClass().getName(), "GoogleApiClient connection failed: " + result.toString());
        if (!result.hasResolution()) {
            // show the localized error dialog.
            GoogleApiAvailability.getInstance().getErrorDialog(this, result.getErrorCode(), 0).show();
            return;
        }
        try {
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            Log.e(getClass().getName(), "Exception while starting resolution activity", e);
        }
    }
}
