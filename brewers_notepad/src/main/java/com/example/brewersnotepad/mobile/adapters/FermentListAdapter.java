package com.example.brewersnotepad.mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.data.FermentationEntry;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.listeners.DeleteListListener;

/**
 * Created by xnml on 13.5.2016 г..
 */
public class FermentListAdapter<T> extends ArrayAdapter<FermentationEntry> {
    private LayoutInflater inflater;
    private DeleteListListener removeListener;
    public FermentListAdapter(Context context, int resource) {
        super(context, resource);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.removeListener = new DeleteListListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.grain_list_item, parent, false);
        }
        FermentationEntry grainEntry = getItem(position);
        TextView grainTypeUi = (TextView)convertView.findViewById(R.id.grain_list_type_entry);
        TextView grainQuantityUi = (TextView)convertView.findViewById(R.id.grain_list_quantity_entry);

        ImageView deleteGrainBtn = (ImageView)convertView.findViewById(R.id.deleteGrainButton);
        deleteGrainBtn.setTag(position);
        deleteGrainBtn.setOnClickListener(removeListener);
//        grainTypeUi.setText(grainEntry.getGrainType());
//        grainQuantityUi.setText(grainEntry.getGrainQuantity()+"lbs");
        return convertView;
    }
}