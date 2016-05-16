package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

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

    private boolean isInputValid(String grainType,String grainQuantity) {
        if(grainType.isEmpty()) {

            Toast.makeText(fragmentView.getContext(), "Please type in \"Grain type\"!", Toast.LENGTH_LONG).show();
            return false;
        } else if (grainQuantity.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), "Please type in \"Quantity\"!", Toast.LENGTH_LONG).show();
            return false ;
        }
        try {
            Double.parseDouble(grainQuantity);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), "Grain quantity is an invalid decimal number!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
            TextView grainTypeInput = ((TextView)fragmentView.findViewById(R.id.inputGrainType));
            TextView grainQuantityInput = ((TextView)fragmentView.findViewById(R.id.inputGrainQuantity));
            String grainType = grainTypeInput.getText().toString();
            String grainQuantity = grainQuantityInput.getText().toString();
            if(isInputValid(grainType,grainQuantity)) {
                GrainEntry grains = new GrainEntry();
                grains.setGrainQuantity(Double.parseDouble(grainQuantity));
                grains.setGrainType(grainType);
                RecipeRuntimeManager.getCurrentRecipe().addGrains(grains);
                grainAdapter.add(grains);
                grainAdapter.notifyDataSetChanged();
                grainTypeInput.setText(null);
                grainQuantityInput.setText(null);
                grainTypeInput.requestFocus();
            }
    }
}
