package com.example.brewersnotepad.mobile.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ArrayAdapter;

/**
 * Created by xnml on 14.5.2016 г..
 */
public class BaseListAdapter<T>  extends ArrayAdapter<T> {

    protected float MAX_HEIGHT;
    public BaseListAdapter(Context context, int resource) {
        super(context, resource);
    }


    protected float getTargetHeight() {
        float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getContext().getResources().getDisplayMetrics());
        float targetHeight = getCount()*val;
        if(targetHeight>MAX_HEIGHT) {
            return MAX_HEIGHT;
        }
        return targetHeight;
    }
}
