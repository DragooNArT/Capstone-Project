package com.example.brewersnotepad.mobile.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brewersnotepad.R;

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
        View childView = rv.findChildViewUnder(e.getX(),e.getY());
        if(childView != null) {
        //TODO find a way to fetch data entry from childView
            //TODO transition to a new activity
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public static class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mRecipeName;

        public RecipeListViewHolder(View view) {
            super(view);
            mRecipeName = (TextView) view.findViewById(R.id.recipe_name);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
