package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.adapters.HopListAdapter;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class DeleteListListener implements ImageButton.OnClickListener {
    private ArrayAdapter adapter;
    public DeleteListListener(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        Object entry = adapter.getItem(pos);
        adapter.remove(entry);
        adapter.notifyDataSetChanged();
    }
}
