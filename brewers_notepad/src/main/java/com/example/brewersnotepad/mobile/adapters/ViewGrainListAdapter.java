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
import com.example.brewersnotepad.mobile.providers.MetricsProvider;

/**
 * Created by DragooNArT-PC on 5/16/2016.
 */
public class ViewGrainListAdapter<T> extends BaseListAdapter<GrainEntry> {


    private LayoutInflater mInflater;
    private MetricsProvider mMetricsProvider;
    private ListView grainViewList;
    public ViewGrainListAdapter(Context context, int resource, MetricsProvider mMetricsProvider,ListView grainViewList) {
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
            convertView = mInflater.inflate(R.layout.grain_list_item_view, parent, false);
        }
        GrainEntry grainEntry = getItem(position);
        TextView grainType = (TextView)convertView.findViewById(R.id.grain_list_type_entry);
        grainType.setText(grainEntry.getGrainType());
        TextView grainQuantity = (TextView)convertView.findViewById(R.id.grain_list_quantity_entry);
        if(grainEntry.getGrainQuantity() > 0) {
            grainQuantity.setText(mMetricsProvider.convertWeightToText(grainEntry.getGrainQuantity()));
        }
        return convertView;
    }

//    @Override
//    public void onBindViewHolder(ViewGrainListViewHolder holder, int position) {
//        GrainEntry grainEntry = mGrainList.get(position);
//        holder.getGrainQuantity().setText(grainEntry.getGrainQuantity());
//    }

//    @Override
//    public int getItemCount() {
//        return mGrainList == null ? 0 : mGrainList.size();
//    }

//    public static class ViewGrainListViewHolder extends RecyclerView.ViewHolder {
//
//        public final TextView mGrainType;
//
//        public final TextView mGrainQuantity;
//
//        public ViewGrainListViewHolder(View view) {
//            super(view);
//            mGrainType = (TextView) view.findViewById(R.id.grain_list_type_entry);
//            mGrainQuantity = (TextView) view.findViewById(R.id.grain_list_quantity_entry);
//        }
//
//        public TextView getGrainQuantity() {
//            return mGrainQuantity;
//        }
//        public TextView getGrainType() {
//            return mGrainQuantity;
//        }
//
//    }
}
