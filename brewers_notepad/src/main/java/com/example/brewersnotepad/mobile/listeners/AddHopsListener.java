package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import com.example.brewersnotepad.mobile.adapters.HopListAdapter;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class AddHopsListener implements ImageButton.OnClickListener {

    private HopListAdapter<HopEntry> hopAdapter;
    private View fragmentView;
    private MetricsProvider metricsProvider;
    public AddHopsListener(HopListAdapter<HopEntry> grainAdapter, View fragmentView,MetricsProvider metricsProvider) {
        this.hopAdapter = grainAdapter;
        this.fragmentView = fragmentView;
        this.metricsProvider = metricsProvider;

    }

    private boolean isInputValid(String hopVariety,String hopType,String hopQuantity,String hopTime) {
        if(hopVariety.isEmpty()) {

            Toast.makeText(fragmentView.getContext(), R.string.Toast_input_hop_variety, Toast.LENGTH_LONG).show();
            return false;
        } else if(hopType.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), R.string.Toast_input_hop_type, Toast.LENGTH_LONG).show();
            return false;
        } else if (hopQuantity.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), R.string.Tost_input_hop_quantity, Toast.LENGTH_LONG).show();
            return false ;
        }  else if (hopTime.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), R.string.Toast_input_hop_time, Toast.LENGTH_LONG).show();
            return false ;
        }
        try {
            metricsProvider.convertMinsToValue(hopTime);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), R.string.Toast_invalid_num, Toast.LENGTH_LONG).show();
            return false;
        }
        try {
            metricsProvider.convertSmallWeightToValue(hopQuantity);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), R.string.Toast_invalid_num, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
            TextView hopVarietyInput = ((TextView)fragmentView.findViewById(R.id.hopVarietyInput));
            TextView hopTypeInput = ((TextView)fragmentView.findViewById(R.id.hopTypeInput));
            TextView hopQuantityInput = ((TextView)fragmentView.findViewById(R.id.hopQuantityInput));
            TextView hopTimeInput = ((TextView)fragmentView.findViewById(R.id.hopTimeAddInput));

            String hopVariety = hopVarietyInput.getText().toString();
            String hopType = hopTypeInput.getText().toString();
            String hopQuantity = hopQuantityInput.getText().toString();
            String hopTime = hopTimeInput.getText().toString();

            if(isInputValid(hopVariety,hopType,hopQuantity,hopTime)) {
                HopEntry hops = new HopEntry();
                hops.setHopVariety(hopVariety);
                hops.setHopQuantity(metricsProvider.convertSmallWeightToValue(hopQuantity));
                hops.setHopType(hopType);
                hops.setTimeToAdd(metricsProvider.convertMinsToValue(hopTime));
                RecipeRuntimeManager.getCurrentRecipe().addHops(hops);
                hopAdapter.add(hops);
                hopAdapter.notifyDataSetChanged();
                //reset ui state
                hopVarietyInput.setText(null);
                hopTypeInput.setText(null);
                hopQuantityInput.setText(null);
                hopTimeInput.setText(null);
                hopVarietyInput.requestFocus();
            }
    }
}
