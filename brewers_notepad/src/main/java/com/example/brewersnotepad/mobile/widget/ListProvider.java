package com.example.brewersnotepad.mobile.widget;

import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.ViewRecipeActivity;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.json.JsonUtility;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;
import com.example.brewersnotepad.mobile.providers.RecipeStorageProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xnml on 18.5.2016 г..
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context ctx;
    private int appWidgetId;
    private List<RecipeDataHolder> recipeList = new ArrayList<RecipeDataHolder>();
    public ListProvider(Context ctx, Intent intent) {
    this.ctx = ctx;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {
        String[] columns = new String[]{RecipeStorageProvider.FIELD_RECIPE_NAME};
        loadData(ctx.getContentResolver().query(RecipeStorageProvider.CONTENT_URI,columns,null,null,null));
    }

    @Override
    public void onDataSetChanged() {
        String[] columns = new String[]{RecipeStorageProvider.FIELD_RECIPE_NAME};
        loadData(ctx.getContentResolver().query(RecipeStorageProvider.CONTENT_URI,columns,null,null,null));
    }

    @Override
    public void onDestroy() {
        recipeList.clear();
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                ctx.getPackageName(), R.layout.widget_list_item);
        remoteView.setTextViewText(R.id.widget_list_item_name, recipeList.get(position).getRecipe_name());
        Intent configIntent = new Intent();
        configIntent.putExtra(ViewRecipeActivity.RECIPE_ID_EXTRA,recipeList.get(position).getRecipe_name());
        remoteView.setOnClickFillInIntent(R.id.widget_item_root, configIntent);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    private void loadData(Cursor data) {
        try {
            if (data != null) {
                data.moveToFirst();
                recipeList.clear();
                while (!data.isAfterLast()) {
                    String recipe_id = data.getString(data.getColumnIndex(RecipeStorageProvider.FIELD_RECIPE_NAME));
                    RecipeDataHolder entry = new RecipeDataHolder();
                    entry.setRecipe_name(recipe_id);
                    recipeList.add(entry);
                    data.moveToNext();
                }
            }
        } finally {
            if(data != null && !data.isClosed()) {
                data.close();
            }
        }
    }

}
