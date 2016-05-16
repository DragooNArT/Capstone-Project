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
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.listeners.DeleteListListener;

/**
 * Created by xnml on 13.5.2016 г..
 */
public class HopListAdapter<T> extends BaseListAdapter<HopEntry> {
    private LayoutInflater mInflater;
    private DeleteListListener deleteListListener;
    private ListView hopsList;
    public HopListAdapter(Context context, int resource, ListView hopsList) {
        super(context, resource);
        MAX_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getContext().getResources().getDisplayMetrics());
        this.hopsList = hopsList;
        mInflater  =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        deleteListListener = new DeleteListListener(this);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Math.round(getTargetHeight()));
        hopsList.setLayoutParams(mParam);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.hop_list_item, parent, false);
        }
        HopEntry hopEntry = getItem(position);
        TextView hopVariety = (TextView)convertView.findViewById(R.id.hop_list_variety_entry);
        hopVariety.setText(hopEntry.getHopVariety());
        TextView hopType = (TextView)convertView.findViewById(R.id.hop_list_type_entry);
        hopType.setText(hopEntry.getHopType());
        TextView hopQuantity = (TextView)convertView.findViewById(R.id.hop_list_quantity_entry);
        hopQuantity.setText(hopEntry.getHopQuantity()+metricsProvider.getSmallWeightPrefix());
        TextView hopAdd = (TextView)convertView.findViewById(R.id.hop_list_when_entry);
        hopAdd.setText(hopEntry.getTimeToAdd()+getContext().getString(R.string.time_in_minutes));
        ImageView deleteGrainBtn = (ImageView)convertView.findViewById(R.id.deleteGrainButton);
        deleteGrainBtn.setTag(position);
        deleteGrainBtn.setOnClickListener(deleteListListener);
        return convertView;
    }

}
