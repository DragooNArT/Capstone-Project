package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.RecipeManager;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class AddGrainListener implements ImageButton.OnClickListener {

    private GrainListAdapter<GrainEntry> grainAdapter;
    private View fragmentView;

    public AddGrainListener(GrainListAdapter<GrainEntry> grainAdapter,View fragmentView) {
        this.grainAdapter = grainAdapter;
        this.fragmentView = fragmentView;
    }


    @Override
    public void onClick(View v) {
            String grainType = ((TextView)fragmentView.findViewById(R.id.inputGrainType)).getText().toString();
            String grainQuantity = ((TextView)fragmentView.findViewById(R.id.inputGrainQuantity)).getText().toString();
            if(grainType.isEmpty()) {

                Toast.makeText(fragmentView.getContext(), "Please type in \"Grain type\"!", Toast.LENGTH_LONG).show();
                return;
            } else if (grainQuantity.isEmpty()) {
                Toast.makeText(fragmentView.getContext(), "Please type in \"Quantity\"!", Toast.LENGTH_LONG).show();
                return;
            }
            GrainEntry grains = new GrainEntry();
            grains.setGrainQuantity(Double.parseDouble(grainQuantity));
            grains.setGrainType(grainType);
            RecipeManager.getCurrentRecipe().addGrains(grains);
            grainAdapter.add(grains);
            grainAdapter.notifyDataSetChanged();
    }
}
