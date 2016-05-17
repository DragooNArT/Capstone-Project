package com.example.brewersnotepad.mobile.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.brewersnotepad.mobile.activities.ViewRecipeActivity;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeStorageProvider;

/**
 * Created by xnml on 17.5.2016 г..
 */
public class ViewBaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    protected String recipeName;
    protected MetricsProvider mMetricsProvider;
    private static final int LOADER_ID = 999999;

    protected RecipeDataHolder currentRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMetricsProvider = new MetricsProvider(getContext());
        Bundle arguments = getArguments();
        if (arguments != null) {
            recipeName = arguments.getString(ViewRecipeActivity.RECIPE_ID_EXTRA);
            getLoaderManager().initLoader(LOADER_ID, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (recipeName != null) {
            String[] columns = new String[]{RecipeStorageProvider.FIELD_RECIPE_DATA};
            String whereClause = RecipeStorageProvider.FIELD_RECIPE_NAME + "= '" + recipeName + "'";
            return new CursorLoader(getContext(), RecipeStorageProvider.CONTENT_URI, columns, whereClause, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
