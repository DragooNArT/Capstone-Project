package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.FermentListAdapter;
import com.example.brewersnotepad.mobile.data.FermentationEntry;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

/**
 * Created by xnml on 13.5.2016 г..
 */
public class AddFermentListener implements ImageButton.OnClickListener {
    private View fragmentView;
    private FermentListAdapter<FermentationEntry> fermentAdapter;
    private MetricsProvider metricsProvider;
    public AddFermentListener(FermentListAdapter<FermentationEntry> fermentAdapter, View fragmentView,MetricsProvider metricsProvider) {
        this.fermentAdapter = fermentAdapter;
        this.fragmentView = fragmentView;
        this.metricsProvider = metricsProvider;
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
            metricsProvider.convertTempForStorage(fermentTemp);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), "Ferment temp is an invalid decimal number!", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            metricsProvider.convertDaysToValue(fermentTime);
        } catch(NumberFormatException e) {
            Toast.makeText(fragmentView.getContext(), "Ferment time is an invalid decimal number!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        TextView fermentPhaseNameInput = ((TextView)fragmentView.findViewById(R.id.fermentPhaseName));
        TextView fermentTempInput = ((TextView)fragmentView.findViewById(R.id.fermentTemp));
        TextView fermentTimeInput = ((TextView)fragmentView.findViewById(R.id.fermentTime));
        String fermentName = fermentPhaseNameInput.getText().toString();
        String fermentTemp = fermentTempInput.getText().toString();

        String fermentTime = fermentTimeInput.getText().toString();
        if(isInputValid(fermentName,fermentTemp,fermentTime)) {
            FermentationEntry fermentPhase = new FermentationEntry();
            fermentPhase.setPhaseName(fermentName);
            fermentPhase.setPhaseDuration(metricsProvider.convertDaysToValue(fermentTime));
            fermentPhase.setTargetPhaseTemp(metricsProvider.convertTempForStorage(fermentTemp));
            RecipeRuntimeManager.getCurrentRecipe().addFermentPhase(fermentPhase);
            fermentAdapter.add(fermentPhase);
            fermentAdapter.notifyDataSetChanged();
            //reset ui state
            fermentPhaseNameInput.setText(null);
            fermentTempInput.setText(null);
            fermentTimeInput.setText(null);
            fermentPhaseNameInput.requestFocus();
        }
    }
}
