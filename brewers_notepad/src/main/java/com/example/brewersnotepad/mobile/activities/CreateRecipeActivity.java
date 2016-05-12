package com.example.brewersnotepad.mobile.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFragmentSecondary;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFramentMain;
import com.example.brewersnotepad.mobile.adapters.CreateRecipePagerAdapter;
import com.example.brewersnotepad.mobile.listeners.CreateRecipeListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class CreateRecipeActivity extends AppCompatActivity implements CreateRecipeFramentMain.OnFragmentInteractionListener, CreateRecipeFragmentSecondary.OnFragmentInteractionListener {

    private CreateRecipePagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private MenuItem doneButton;
    private RecipeDataHolder recipeInstance;
    private CreateRecipeListener listener;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        listener = new CreateRecipeListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_recipe_toolbar);
        toolbar.setTitle(getString(R.string.create_recipe_view_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new CreateRecipePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.create_recpie_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        recipeInstance = new RecipeDataHolder();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public RecipeDataHolder getRecipeInstance() {
        return recipeInstance;
    }
    public void onAddGrains(View v) {
        String grainType = ((TextView)findViewById(R.id.inputGrainType)).getText().toString();
        String grainQuantity = ((TextView)findViewById(R.id.inputGrainQuantity)).getText().toString();
        if(grainType.isEmpty()) {

            Toast.makeText(this, "Please type in \"Grain type\"!", Toast.LENGTH_LONG).show();
            return;
        } else if (grainQuantity.isEmpty()) {
            Toast.makeText(this, "Please type in \"Quantity\"!", Toast.LENGTH_LONG).show();
            return;
        }
        GrainEntry grains = new GrainEntry();
        grains.setGrainQuantity(Double.parseDouble(grainQuantity));
        grains.setGrainType(grainType);
        recipeInstance.addGrains(grains);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateRecipe Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.brewersnotepad.mobile.activities/http/host/path")
        );
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CreateRecipe Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.brewersnotepad.mobile.activities/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
