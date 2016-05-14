package com.example.brewersnotepad.mobile.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.data.RecipeManager;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeExtrasFragment;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFragmentSecondary;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFramentMain;
import com.example.brewersnotepad.mobile.adapters.CreateRecipePagerAdapter;
import com.example.brewersnotepad.mobile.listeners.CreateRecipeListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class CreateRecipeActivity extends AppCompatActivity implements CreateRecipeFramentMain.OnFragmentInteractionListener, CreateRecipeFragmentSecondary.OnFragmentInteractionListener, CreateRecipeExtrasFragment.OnFragmentInteractionListener {

    private CreateRecipePagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MenuItem doneButton;
    private RecipeDataHolder recipeInstance;
    private CreateRecipeListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        listener = new CreateRecipeListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_recipe_toolbar);
        toolbar.setTitle(getString(R.string.create_recipe_view_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new CreateRecipePagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.create_recpie_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //create a new recipe and make it globally available
        recipeInstance = new RecipeDataHolder();
        RecipeManager.setCurrentRecipe(recipeInstance);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public RecipeDataHolder getRecipeInstance() {
        return recipeInstance;
    }


    public void onAddHops(View v) {
        String hopType = ((TextView)findViewById(R.id.inputGrainType)).getText().toString();
        String hopQuantity = ((TextView)findViewById(R.id.inputGrainQuantity)).getText().toString();
        if(hopType.isEmpty()) {

            Toast.makeText(this, "Please type in \"Hop type\"!", Toast.LENGTH_LONG).show();
            return;
        } else if (hopQuantity.isEmpty()) {
            Toast.makeText(this, "Please type in \"Quantity\"!", Toast.LENGTH_LONG).show();
            return;
        }
        HopEntry hops = new HopEntry();
        hops.setHopQuantity(Integer.parseInt(hopQuantity));
        hops.setHopType(hopType);
        recipeInstance.addHops(hops);
    }

    public MenuItem getDoneButton() {
        return doneButton;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //discard the new recipe
        RecipeManager.setCurrentRecipe(null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_recipe_menu, menu);
        doneButton = menu.findItem(R.id.recipe_done);
        doneButton.setOnMenuItemClickListener(listener);
        return true;
    }

}
