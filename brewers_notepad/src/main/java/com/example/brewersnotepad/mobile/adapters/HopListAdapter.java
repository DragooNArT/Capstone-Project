package com.example.brewersnotepad.mobile.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.listeners.DeleteListListener;

/**
 * Created by xnml on 13.5.2016 г..
 */
public class HopListAdapter<T> extends ArrayAdapter<HopEntry> {
    private static  float MAX_HEIGHT ;
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
            convertView = mInflater.inflate(R.layout.grain_list_item, parent, false);
        }
        HopEntry hopEntry = getItem(position);
        TextView grainTypeUi = (TextView)convertView.findViewById(R.id.grain_list_type_entry);
        TextView grainQuantityUi = (TextView)convertView.findViewById(R.id.grain_list_quantity_entry);

        ImageView deleteGrainBtn = (ImageView)convertView.findViewById(R.id.deleteGrainButton);
        deleteGrainBtn.setTag(position);
        deleteGrainBtn.setOnClickListener(deleteListListener);
//        grainTypeUi.setText(hopEntry.getGrainType());
//        grainQuantityUi.setText(hopEntry.getGrainQuantity()+"lbs");
        return convertView;
    }

    public float getTargetHeight() {
        float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getContext().getResources().getDisplayMetrics());
        float targetHeight = getCount()*val;
        if(targetHeight>MAX_HEIGHT) {
            return MAX_HEIGHT;
        }
        return targetHeight;
    }
}
