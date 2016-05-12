package com.example.brewersnotepad.mobile.listeners;

import android.view.View;
import android.widget.ImageButton;

import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.data.GrainEntry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class GrainRemoveListener implements ImageButton.OnClickListener {
    private GrainListAdapter<?> tGrainListAdapter;
    Map<Integer,Integer> listMapping = new HashMap<Integer,Integer>();
    public  GrainRemoveListener(GrainListAdapter<?> tGrainListAdapter) {
        this.tGrainListAdapter = tGrainListAdapter;
    }

    public void addEntry(Integer id,Integer pos) {
        listMapping.put(id,pos);
    }
    @Override
    public void onClick(View v) {
        int pos = listMapping.get(v.getId());
        GrainEntry entry = tGrainListAdapter.getItem(pos);
        tGrainListAdapter.remove(entry);
        tGrainListAdapter.notifyDataSetChanged();
    }
}
