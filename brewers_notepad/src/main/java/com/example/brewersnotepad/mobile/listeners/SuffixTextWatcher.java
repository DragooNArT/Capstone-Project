package com.example.brewersnotepad.mobile.listeners;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by DragooNArT-PC on 5/17/2016.
 */
public class SuffixTextWatcher implements TextWatcher,View.OnFocusChangeListener {
    private EditText view;
    private String suffix;
    public SuffixTextWatcher(EditText view,String suffix) {
        this.view = view;
        view.setOnFocusChangeListener(this);
        this.suffix = suffix;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(isFocused && (editable.toString().isEmpty() || !editable.toString().endsWith(suffix))){
            view.setText(suffix);
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setSelection(0);
                }
            });
        }
    }
    private boolean isFocused = false;

    @Override
    public void onFocusChange(View view, boolean focused) {
        if(this.view == view) {
            isFocused = focused;

            final EditText localView = this.view;
            if (focused && this.view.getText().length() == 0) {
                localView.setText(suffix);

            } else if(!focused && suffix.equals(this.view.getText().toString())) {
                localView.setText(null);
            }
            if(focused) {
                localView.post(new Runnable() {
                    @Override
                    public void run() {
                        localView.setSelection(0);
                    }
                });
            }
        }
    }
}
