package com.example.brewersnotepad.mobile.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;

/**
 * Created by DragooNArT-PC on 5/16/2016.
 */
public class ViewHopListAdapter<T> extends BaseListAdapter<HopEntry> {


    private LayoutInflater mInflater;
    private MetricsProvider mMetricsProvider;
    private ListView grainViewList;
    public ViewHopListAdapter(Context context, int resource, MetricsProvider mMetricsProvider, ListView grainViewList) {
        super(context,resource);
        this.grainViewList = grainViewList;
        this.mMetricsProvider = mMetricsProvider;
        mInflater  =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MAX_HEIGHT = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Math.round(getTargetHeight()));
        grainViewList.setLayoutParams(mParam);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.hop_list_item_view, parent, false);
        }
        HopEntry hopEntry = getItem(position);
        TextView hopVariety = (TextView)convertView.findViewById(R.id.view_hop_list_variety_entry);
        hopVariety.setText(hopEntry.getHopVariety());
        TextView hopType = (TextView)convertView.findViewById(R.id.view_hop_list_type_entry);
        hopType.setText(hopEntry.getHopType());
        TextView hopQuantity = (TextView)convertView.findViewById(R.id.view_hop_list_quantity_entry);
        if(hopEntry.getHopQuantity() > 0) {
            hopQuantity.setText(mMetricsProvider.convertSmallWeightToText(hopEntry.getHopQuantity()));
        }
        TextView hopWhen = (TextView)convertView.findViewById(R.id.view_hop_list_when_entry);
        if(hopEntry.getTimeToAdd() > 0) {
            hopWhen.setText(mMetricsProvider.convertMinsToText(hopEntry.getTimeToAdd()));
        }
        return convertView;
    }

}
