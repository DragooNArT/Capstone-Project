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
import com.example.brewersnotepad.mobile.data.FermentationEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;

/**
 * Created by DragooNArT-PC on 5/16/2016.
 */
public class ViewFermentListAdapter<T> extends BaseListAdapter<FermentationEntry> {


    private LayoutInflater mInflater;
    private MetricsProvider mMetricsProvider;
    private ListView grainViewList;
    public ViewFermentListAdapter(Context context, int resource, MetricsProvider mMetricsProvider, ListView grainViewList) {
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
            convertView = mInflater.inflate(R.layout.ferment_list_item_view, parent, false);
        }
        FermentationEntry fermentEntry = getItem(position);
        TextView phaseName = (TextView)convertView.findViewById(R.id.ferment_list_name_entry);
        phaseName.setText(fermentEntry.getPhaseName());
        TextView daysDuration = (TextView)convertView.findViewById(R.id.ferment_list_duration_entry);
        if(fermentEntry.getPhaseDuration() > 0) {
            daysDuration.setText(mMetricsProvider.convertDaysToText(fermentEntry.getPhaseDuration()));
        }
        TextView targetTemp = (TextView)convertView.findViewById(R.id.ferment_list_temp_entry);
        if(fermentEntry.getTargetPhaseTemp() != Integer.MAX_VALUE) {
            targetTemp.setText(mMetricsProvider.convertTempToText(fermentEntry.getTargetPhaseTemp()));
        }
        return convertView;
    }

}
