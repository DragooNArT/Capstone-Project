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
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.listeners.DeleteListListener;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class GrainListAdapter<T> extends BaseListAdapter<GrainEntry> {
    private LayoutInflater inflater;
    private DeleteListListener removeListener;
    private ListView grainList;
    public GrainListAdapter(Context context, int resource,ListView grainList) {
        super(context, resource);
        this.grainList = grainList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MAX_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getContext().getResources().getDisplayMetrics());
        removeListener = new DeleteListListener(this);
    }

    @Override
    public void remove(GrainEntry object) {
        RecipeRuntimeManager.getCurrentRecipe().getRecipe_grains().remove(object);
        super.remove(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Math.round(getTargetHeight()));
        grainList.setLayoutParams(mParam);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.grain_list_item, parent, false);
        }
        GrainEntry grainEntry = getItem(position);
        TextView grainTypeUi = (TextView)convertView.findViewById(R.id.grain_list_type_entry);
        TextView grainQuantityUi = (TextView)convertView.findViewById(R.id.grain_list_quantity_entry);


        ImageView deleteGrainBtn = (ImageView)convertView.findViewById(R.id.deleteGrainButton);
        deleteGrainBtn.setTag(position);
        //TODO always removes last element(fixme)
        deleteGrainBtn.setOnClickListener(removeListener);
        grainTypeUi.setText(grainEntry.getGrainType());
        grainQuantityUi.setText(metricsProvider.getWeightText(grainEntry.getGrainQuantity()));
        return convertView;
    }
}
