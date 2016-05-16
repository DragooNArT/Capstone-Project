package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ImageButton;

import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class DeleteGrainListener implements ImageButton.OnClickListener {
    private GrainListAdapter<?> tGrainListAdapter;
    public DeleteGrainListener(GrainListAdapter<?> tGrainListAdapter) {
        this.tGrainListAdapter = tGrainListAdapter;
    }


    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        GrainEntry entry = tGrainListAdapter.getItem(pos);
        RecipeRuntimeManager.getCurrentRecipe().getRecipe_grains().remove(pos);
        tGrainListAdapter.remove(entry);
        tGrainListAdapter.notifyDataSetChanged();
    }
}
