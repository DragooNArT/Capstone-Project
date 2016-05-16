package com.example.brewersnotepad.mobile.activities;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.CursorLoader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.CreateRecipePagerAdapter;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeExtrasFragment;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFragmentSecondary;
import com.example.brewersnotepad.mobile.fragments.CreateRecipeFramentMain;
import com.example.brewersnotepad.mobile.json.JsonUtility;
import com.example.brewersnotepad.mobile.listeners.CreateRecipeListener;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;
import com.example.brewersnotepad.mobile.providers.RecipeStorageProvider;

public class CreateRecipeActivity extends AppCompatActivity implements CreateRecipeFramentMain.OnFragmentInteractionListener, CreateRecipeFragmentSecondary.OnFragmentInteractionListener, CreateRecipeExtrasFragment.OnFragmentInteractionListener {

    private CreateRecipePagerAdapter mSectionsPagerAdapter;
    private final int LOADER_ID = 11;
    private ViewPager mViewPager;
    private MenuItem doneButton;
    private RecipeDataHolder recipeInstance;
    private CreateRecipeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.create_recipe_toolbar);
        toolbar.setTitle(getString(R.string.create_recipe_view_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new CreateRecipePagerAdapter(getSupportFragmentManager(), this);
        listener = new CreateRecipeListener(this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.create_recpie_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        //create a new recipe and make it globally available
        recipeInstance = new RecipeDataHolder();
        RecipeRuntimeManager.setCurrentRecipe(recipeInstance);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public RecipeDataHolder getRecipeInstance() {
        return recipeInstance;
    }

    public MenuItem getDoneButton() {
        return doneButton;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //discard the new recipe
        RecipeRuntimeManager.setCurrentRecipe(null);
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

    public void fillData(RecipeDataHolder dataHolder) {
        ContentValues values = new ContentValues();
        values.put(RecipeStorageProvider.FIELD_RECIPE_NAME, dataHolder.getRecipe_name());
        String jsonData = JsonUtility.ObjectToJson(dataHolder);
        values.put(RecipeStorageProvider.FIELD_RECIPE_DATA, jsonData);
        Uri insert_uri = getContentResolver().insert(RecipeStorageProvider.CONTENT_URI, values);
        getContentResolver().notifyChange(insert_uri, null);
        dataHolder.setRecipeUri(insert_uri);
    }

}
