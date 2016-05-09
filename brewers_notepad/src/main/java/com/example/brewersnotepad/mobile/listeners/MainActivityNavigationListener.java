package com.example.brewersnotepad.mobile.listeners;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.home.AboutFragment;
import com.example.brewersnotepad.mobile.activities.home.MainActivity;
import com.example.brewersnotepad.mobile.activities.home.PreferencesFragment;
import com.example.brewersnotepad.mobile.activities.home.RecipeListFragment;

/**
 * Created by DragooNArT-PC on 5/9/2016.
 */
public class MainActivityNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

    MainActivity activity;
    public MainActivityNavigationListener(MainActivity activity) {
        this.activity = activity;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_preferences) {
            swapFragment(new PreferencesFragment());
        } else if (id == R.id.nav_about) {
            swapFragment(new AboutFragment());
        } else if( id == R.id.nav_home) {
            swapFragment(new RecipeListFragment());
        }

        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void swapFragment(Fragment fragment) {

        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}
