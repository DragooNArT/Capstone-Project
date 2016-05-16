package com.example.brewersnotepad.mobile.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.data.FermentationEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.listeners.DeleteListListener;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

/**
 * Created by xnml on 13.5.2016 г..
 */
public class FermentListAdapter<T> extends BaseListAdapter<FermentationEntry> {
    private LayoutInflater inflater;
    private ListView mFermentList;
    private DeleteListListener removeListener;
    public FermentListAdapter(Context context, int resource,ListView fermentList) {
        super(context, resource);
        this.mFermentList = fermentList;
        MAX_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getContext().getResources().getDisplayMetrics());
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.removeListener = new DeleteListListener(this);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Math.round(getTargetHeight()));
        mFermentList.setLayoutParams(mParam);
    }

    @Override
    public void remove(FermentationEntry object) {
        RecipeRuntimeManager.getCurrentRecipe().getFermentation_phases().remove(object);
        super.remove(object);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.ferment_list_item, parent, false);
        }
        FermentationEntry fermentationEntry = getItem(position);
        TextView fermentPhaseName = (TextView)convertView.findViewById(R.id.ferment_list_name_entry);
        fermentPhaseName.setText(fermentationEntry.getPhaseName());
        TextView fermentDuration = (TextView)convertView.findViewById(R.id.ferment_list_duration_entry);
        fermentDuration.setText(getContext().getString(R.string.fermentTimeFormat,fermentationEntry.getPhaseDuration()));

        TextView fermentTemp = (TextView)convertView.findViewById(R.id.ferment_list_temp_entry);
        fermentTemp.setText(fermentationEntry.getTargetPhaseTemp()+" "+metricsProvider.getTempPrefix());
        ImageView deleteGrainBtn = (ImageView)convertView.findViewById(R.id.deleteGrainButton);
        deleteGrainBtn.setTag(position);
        deleteGrainBtn.setOnClickListener(removeListener);
        return convertView;
    }
}
