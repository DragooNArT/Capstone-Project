package com.example.brewersnotepad.mobile.adapters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.ViewRecipeActivity;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.json.JsonUtility;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;
import com.example.brewersnotepad.mobile.providers.RecipeStorageProvider;

import java.util.List;

/**
 * Created by DragooNArT-PC on 5/8/2016.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> implements  View.OnClickListener {
    private View mEmptyView;
    private Activity mActivity;


    public RecipeListAdapter(Activity activity, View emptyView) {
        this.mEmptyView = emptyView;
        this.mActivity = activity;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( parent instanceof RecyclerView ) {
            int layoutId = -1;
            switch (viewType) {
                default: {
                    layoutId = R.layout.beer_list_item;
                    break;
                }
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            view.setFocusable(true);

            return new RecipeListViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        if(RecipeRuntimeManager.hasRecipes()) {
            RecipeDataHolder recipeEntry = RecipeRuntimeManager.getRecipesList().get(position);
            holder.mRecipeName.setText(recipeEntry.getRecipe_name());
        }

    }

    @Override
    public int getItemCount() {
        if(RecipeRuntimeManager.hasRecipes()) {
            return RecipeRuntimeManager.getRecipesList().size();
        }
        return 0;
    }



    @Override
    public void onClick(View view) {
//
//        View childView = rv.findChildViewUnder(e.getX(),e.getY());
//
//        if(childView != null && e.getAction() == MotionEvent.ACTION_UP) {
//            TextView recipe_nameView = (TextView)childView.findViewById(R.id.recipe_name);
//
//            String recipeName = (String) recipe_nameView.getText();
//            Intent intent = new Intent(mActivity, ViewRecipeActivity.class);
//            //TODO put stuff
//            mActivity.startActivity(intent);
//            //TODO find a way to fetch data entry from childView
//            //TODO transition to a new activity
//        }
    }

    public static class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mRecipeName;
        public final View viewRoot;

        public RecipeListViewHolder(View view) {
            super(view);

            view.setClickable(true);
            view.setOnClickListener(this);
            mRecipeName = (TextView) view.findViewById(R.id.recipe_name);
            viewRoot = view.findViewById(R.id.recipe_list_item_root);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            TextView recipe_nameView = (TextView)view.findViewById(R.id.recipe_name);

            String[] columns = new String[]{RecipeStorageProvider.FIELD_RECIPE_DATA};
            String selection = RecipeStorageProvider.FIELD_RECIPE_NAME+" = '"+recipe_nameView.getText()+"'";

            Cursor r = viewRoot.getContext().getContentResolver().query(RecipeStorageProvider.CONTENT_URI,columns,selection,null,null);
            if(r != null && r.moveToFirst()) {
                String jsonData = r.getString(r.getColumnIndex(RecipeStorageProvider.FIELD_RECIPE_DATA));
                RecipeDataHolder recipe = JsonUtility.JsonToObject(jsonData);
                if(recipe != null) {
                    addOrReplaceRecipe(recipe);
                } else {
                    Log.e(getClass().getName(),"unable to parse json from db");
                }
            }

            Intent intent = new Intent(view.getContext(), ViewRecipeActivity.class);
//            //TODO put stuff
            view.getContext().startActivity(intent);
//            //TODO find a way to fetch data entry from childView
//            //TODO transition to a new activity
        }
    }

    private static void addOrReplaceRecipe(RecipeDataHolder recipe) {
        int pos = -1;
        for(RecipeDataHolder entry : RecipeRuntimeManager.getRecipesList()) {
            if(recipe.getRecipe_name().equals(entry.getRecipe_name())){
                pos = RecipeRuntimeManager.getRecipesList().indexOf(entry);
                break;
            }
        }
        if(pos > -1) {
            RecipeRuntimeManager.getRecipesList().remove(pos);
            RecipeRuntimeManager.getRecipesList().add(pos,recipe);
        }
    }
}
