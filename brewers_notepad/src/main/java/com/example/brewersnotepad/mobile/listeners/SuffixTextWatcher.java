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
        if(editable.toString().isEmpty() || !editable.toString().endsWith(suffix)){
            view.setText(suffix);
            Selection.setSelection(view.getText(),0);
        }
    }



    @Override
    public void onFocusChange(View view, boolean b) {
        if(b && this.view == view) {
            if (this.view.getText().length() == 0) {
                this.view.setText(suffix);
                Selection.setSelection(this.view.getText(),0);
            }
        }
    }
}
