package com.example.brewersnotepad.mobile.listeners;

import android.text.BoringLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import static android.widget.Toast.*;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class AddGrainListener implements ImageButton.OnClickListener {

    private GrainListAdapter<GrainEntry> grainAdapter;
    private View fragmentView;
    private MetricsProvider mMetricsProvider;
    public AddGrainListener(GrainListAdapter<GrainEntry> grainAdapter,View fragmentView,MetricsProvider metricsProvider) {
        this.mMetricsProvider = metricsProvider;
        this.grainAdapter = grainAdapter;
        this.fragmentView = fragmentView;
    }

    private boolean isInputValid(String grainType,String grainQuantity) {
        if(grainType.isEmpty()) {

            makeText(fragmentView.getContext(), R.string.Toast_input_grainType, LENGTH_LONG).show();
            return false;
        } else if (grainQuantity.isEmpty()) {
            makeText(fragmentView.getContext(), R.string.Toast_input_grain_quantity, LENGTH_LONG).show();
            return false ;
        }
        try {
            mMetricsProvider.convertWeightTextForStorage(grainQuantity);
        } catch(NumberFormatException e) {
            makeText(fragmentView.getContext(), R.string.Toast_grain_quantity_invalid, LENGTH_LONG).show();
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
                grains.setGrainQuantity(mMetricsProvider.convertWeightTextForStorage(grainQuantity));
                grains.setGrainType(grainType);
                RecipeRuntimeManager.getCurrentRecipe().addGrains(grains);
                grainAdapter.add(grains);
                grainAdapter.notifyDataSetChanged();
                //reset ui state
                grainTypeInput.setText(null);
                grainQuantityInput.setText(null);
                grainTypeInput.requestFocus();
            }
    }
}
