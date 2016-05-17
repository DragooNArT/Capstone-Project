package com.example.brewersnotepad.mobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.ViewGrainListAdapter;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewRecipeMain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ViewRecipeMain extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecipeDataHolder currentRecipe;
    MetricsProvider mMetricsProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMetricsProvider = new MetricsProvider(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_view_recipe_main, container, false);
        fill_In_View(view);

        return view;
    }

    void fill_In_View(View view) {
        currentRecipe = RecipeRuntimeManager.getViewRecipe();
        ListView grainList = (ListView)view.findViewById(R.id.view_grain_list);
        ViewGrainListAdapter adapter = new ViewGrainListAdapter<GrainEntry>(getContext(),
                android.R.layout.simple_list_item_1,mMetricsProvider,grainList);
        grainList.setAdapter(adapter);
        if(currentRecipe.getRecipe_grains().size()>0) {
            adapter.addAll(currentRecipe.getRecipe_grains());
        } else {
            List<GrainEntry> dummyEntry = new ArrayList<GrainEntry>();
            GrainEntry noEntry = new GrainEntry();
            noEntry.setGrainType(getContext().getString(R.string.no_entry_hint));
            dummyEntry.add(noEntry);
            adapter.addAll(dummyEntry);
        }
        adapter.notifyDataSetChanged();

        //Recipe Name
        TextView recipeName = (TextView)view.findViewById(R.id.viewRecipeName);
        recipeName.setText(currentRecipe.getRecipe_name());

        //Recipe Type
        String recipeTypeValue = currentRecipe.getRecipe_type();
        if(recipeTypeValue!=null && !recipeTypeValue.isEmpty()) {
            TextView recipeType = (TextView)view.findViewById(R.id.viewRecipeType);
            recipeType.setText(recipeTypeValue);
        }

        //Mash duration
        int mashDuration = currentRecipe.getMashDuration();
        if(mashDuration>0) {
            TextView mashDurationView = (TextView)view.findViewById(R.id.viewMashDuration);
            mashDurationView.setText(mMetricsProvider.convertMinsToText(mashDuration));
        }

        //Mash temp
        double mashTemp = currentRecipe.getMashTemp();
        TextView mashTempView = (TextView)view.findViewById(R.id.viewMashTemp);
        mashTempView.setText(mMetricsProvider.convertTempToText(mashTemp));


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
