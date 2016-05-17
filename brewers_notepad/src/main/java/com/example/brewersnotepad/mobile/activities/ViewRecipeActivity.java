package com.example.brewersnotepad.mobile.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.ViewRecipePagerAdapter;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeExtrasFragment;
import com.example.brewersnotepad.mobile.fragments.ViewRecipeMain;
import com.example.brewersnotepad.mobile.fragments.ViewRecipeSecondary;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

public class ViewRecipeActivity extends AppCompatActivity implements CreateRecipeExtrasFragment.OnFragmentInteractionListener,ViewRecipeMain.OnFragmentInteractionListener,ViewRecipeSecondary.OnFragmentInteractionListener {
    
    public static final String RECIPE_ID_EXTRA = "recipeIdExtra";
    
    private String recipeName;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ViewRecipePagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.viewRecipeToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            recipeName = extras.getString(RECIPE_ID_EXTRA);
            mSectionsPagerAdapter = new ViewRecipePagerAdapter(getSupportFragmentManager(),recipeName);

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.view_recpie_pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_recipe, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_recipe) {
            Intent intent = new Intent(this, CreateRecipeActivity.class);
            intent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA,recipeName);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
