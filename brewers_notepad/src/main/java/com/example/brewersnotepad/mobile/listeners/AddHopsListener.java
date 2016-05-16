package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import com.example.brewersnotepad.mobile.adapters.HopListAdapter;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class AddHopsListener implements ImageButton.OnClickListener {

    private HopListAdapter<HopEntry> hopAdapter;
    private View fragmentView;

    public AddHopsListener(HopListAdapter<HopEntry> grainAdapter, View fragmentView) {
        this.hopAdapter = grainAdapter;
        this.fragmentView = fragmentView;
    }

    private boolean isInputValid(String hopVariety,String hopType,String hopQuantity,String hopTime) {
        if(hopVariety.isEmpty()) {

            Toast.makeText(fragmentView.getContext(), "Please type in \"Hop variety\"!", Toast.LENGTH_LONG).show();
            return false;
        } else if(hopType.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), "Please type in \"Hop type\"!", Toast.LENGTH_LONG).show();
            return false;
        } else if (hopQuantity.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), "Please type in \"The quantity of hops\"!", Toast.LENGTH_LONG).show();
            return false ;
        }  else if (hopTime.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), "Please type in \"When to add hops\"!", Toast.LENGTH_LONG).show();
            return false ;
        }
        try {
            Integer.parseInt(hopTime);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), "Hop time is an invalid decimal number!", Toast.LENGTH_LONG).show();
            return false;
        }
        try {
            Integer.parseInt(hopQuantity);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), "Hop quantity is an invalid decimal number!", Toast.LENGTH_LONG).show();
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
                hops.setHopQuantity(Integer.parseInt(hopQuantity));
                hops.setHopType(hopType);
                hops.setTimeToAdd(Integer.parseInt(hopTime));
                RecipeRuntimeManager.getCurrentRecipe().addHops(hops);
                hopAdapter.add(hops);
                hopAdapter.notifyDataSetChanged();
                hopVarietyInput.setText(null);
                hopTypeInput.setText(null);
                hopQuantityInput.setText(null);
                hopTimeInput.setText(null);
                hopVarietyInput.requestFocus();
            }
    }
}
