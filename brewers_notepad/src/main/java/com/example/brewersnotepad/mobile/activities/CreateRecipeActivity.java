package com.example.brewersnotepad.mobile.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFragmentSecondary;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFramentMain;
import com.example.brewersnotepad.mobile.adapters.CreateRecipePagerAdapter;

public class CreateRecipeActivity extends AppCompatActivity implements CreateRecipeFramentMain.OnFragmentInteractionListener,CreateRecipeFragmentSecondary.OnFragmentInteractionListener {

    private CreateRecipePagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MenuItem doneButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_recipe_toolbar);
        toolbar.setTitle("Create a new recipe");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new CreateRecipePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.create_recpie_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public MenuItem getDoneButton() {
        return doneButton;
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_recipe_menu, menu);
        doneButton = menu.findItem(R.id.recipe_done);
        return true;
    }
}
