package com.example.brewersnotepad.mobile.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.fragments.MainAboutFragment;
import com.example.brewersnotepad.mobile.fragments.MainPreferencesFragment;
import com.example.brewersnotepad.mobile.fragments.MainRecipeListFragment;
import com.example.brewersnotepad.mobile.listeners.MainActivityNavigationListener;

public class MainActivity extends AppCompatActivity implements MainRecipeListFragment.OnFragmentInteractionListener, MainAboutFragment.OnFragmentInteractionListener, MainPreferencesFragment.OnFragmentInteractionListener {


    public static final String FRAGMENT_STATE = "FRAGMENT_ID";
    private MainActivityNavigationListener navListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navListener = new MainActivityNavigationListener(this);
        navigationView.setNavigationItemSelectedListener(navListener);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

        outState.putInt(FRAGMENT_STATE, navListener.getLastSelectedItemId());
    }

    public boolean isSignedIn() {
        return false; //TODO do real stuff
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
