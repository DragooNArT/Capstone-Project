package com.example.brewersnotepad.mobile.listeners;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.fragments.MainPreferencesFragment;
import com.example.brewersnotepad.mobile.fragments.MainAboutFragment;
import com.example.brewersnotepad.mobile.activities.MainActivity;
import com.example.brewersnotepad.mobile.fragments.MainRecipeListFragment;

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
            swapFragment(new MainPreferencesFragment());
        } else if (id == R.id.nav_about) {
            swapFragment(new MainAboutFragment());
        } else if( id == R.id.nav_home) {
            swapFragment(new MainRecipeListFragment());
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
