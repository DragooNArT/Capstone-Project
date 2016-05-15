package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.FermentListAdapter;
import com.example.brewersnotepad.mobile.data.FermentationEntry;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

/**
 * Created by xnml on 13.5.2016 г..
 */
public class AddFermentListener implements ImageButton.OnClickListener {
    private View fragmentView;
    private FermentListAdapter<FermentationEntry> fermentAdapter;
    public AddFermentListener(FermentListAdapter<FermentationEntry> fermentAdapter, View fragmentView) {
        this.fermentAdapter = fermentAdapter;
        this.fragmentView = fragmentView;
    }

    private boolean isInputValid(String fermentName,String fermentTemp,String fermentTime) {
        if(fermentName.isEmpty()) {

            Toast.makeText(fragmentView.getContext(), "Please type in \"Ferment phase name\"!", Toast.LENGTH_LONG).show();
            return false;
        } else if (fermentTemp.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), "Please type in \"Ferment temp\"!", Toast.LENGTH_LONG).show();
            return false ;
        } else if (fermentTime.isEmpty()) {
            Toast.makeText(fragmentView.getContext(), "Please type in \"Ferment time\"!", Toast.LENGTH_LONG).show();
            return false ;
        }

        try {
            Integer.parseInt(fermentTemp);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), "Ferment temp is an invalid decimal number!", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Integer.parseInt(fermentTime);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), "Ferment time is an invalid decimal number!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        String fermentName = ((TextView)fragmentView.findViewById(R.id.fermentPhaseName)).getText().toString();
        String fermentTemp = ((TextView)fragmentView.findViewById(R.id.fermentTemp)).getText().toString();

        String fermentTime = ((TextView)fragmentView.findViewById(R.id.fermentTime)).getText().toString();
        if(isInputValid(fermentName,fermentTemp,fermentTime)) {
            FermentationEntry fermentPhase = new FermentationEntry();
            fermentPhase.setPhaseName(fermentName);
            fermentPhase.setPhaseDuration(Integer.parseInt(fermentTime));
            fermentPhase.setTargetPhaseTemp(Integer.parseInt(fermentTemp));
            RecipeRuntimeManager.getCurrentRecipe().addFermentPhase(fermentPhase);
            fermentAdapter.add(fermentPhase);
            fermentAdapter.notifyDataSetChanged();
        }
    }
}
