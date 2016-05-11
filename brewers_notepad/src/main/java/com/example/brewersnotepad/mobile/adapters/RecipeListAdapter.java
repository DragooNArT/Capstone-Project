package com.example.brewersnotepad.mobile.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.ViewRecipeActivity;

/**
 * Created by DragooNArT-PC on 5/8/2016.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> implements  RecyclerView.OnItemTouchListener {
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
    String[] dummyData = new String[]{"Recepie 1","Recepie 2","Recepie 3"};

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
    String dummyDataEntry = dummyData[position];
        holder.mRecipeName.setText(dummyDataEntry);
    }

    @Override
    public int getItemCount() {
        if ( null == dummyData ) return 0;
        return dummyData.length;
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(),e.getY());

        if(childView != null && e.getAction() == MotionEvent.ACTION_UP) {
            TextView recipe_nameView = (TextView)childView.findViewById(R.id.recipe_name);

            String recipeName = (String) recipe_nameView.getText();
            Intent intent = new Intent(mActivity, ViewRecipeActivity.class);
            //TODO put stuff
            mActivity.startActivity(intent);
            //TODO find a way to fetch data entry from childView
            //TODO transition to a new activity
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public static class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mRecipeName;
        public final View viewRoot;

        public RecipeListViewHolder(View view) {
            super(view);

            view.setClickable(true);
            mRecipeName = (TextView) view.findViewById(R.id.recipe_name);
            viewRoot = view.findViewById(R.id.recipe_list_item_root);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
