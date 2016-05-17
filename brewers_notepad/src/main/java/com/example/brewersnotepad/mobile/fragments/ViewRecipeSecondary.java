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
import com.example.brewersnotepad.mobile.adapters.ViewFermentListAdapter;
import com.example.brewersnotepad.mobile.adapters.ViewHopListAdapter;
import com.example.brewersnotepad.mobile.data.FermentationEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;


public class ViewRecipeSecondary extends Fragment {

    private OnFragmentInteractionListener mListener;
    private MetricsProvider metricsProvider;

    public ViewRecipeSecondary() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        metricsProvider = new MetricsProvider(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_recipe_secondary, container, false);
        ListView fermentList = (ListView) view.findViewById(R.id.viewFermentList);
        ViewFermentListAdapter<FermentationEntry> fermentAdapter = new ViewFermentListAdapter<>(getContext(), android.R.layout.simple_list_item_1, metricsProvider, fermentList);
        fermentList.setAdapter(fermentAdapter);

        ListView hopList = (ListView) view.findViewById(R.id.viewHopList);
        ViewHopListAdapter<HopEntry> hopAdapter = new ViewHopListAdapter<>(getContext(), android.R.layout.simple_list_item_1, metricsProvider, hopList);
        hopList.setAdapter(hopAdapter);
        fill_In_View(view, fermentAdapter, hopAdapter);
        return view;
    }

    private void fill_In_View(View view, ViewFermentListAdapter fermentAdapter, ViewHopListAdapter hopAdapter) {
        RecipeDataHolder currentRecipe = RecipeRuntimeManager.getViewRecipe();
        if (currentRecipe != null) {
            TextView hopDurationView = (TextView) view.findViewById(R.id.viewHopPhaseDurationInput);
            if (currentRecipe.getHopSteepDuration() > 0) {
                hopDurationView.setText(metricsProvider.convertMinsToText(currentRecipe.getHopSteepDuration()));
            }
            fermentAdapter.clear();
            fermentAdapter.addAll(currentRecipe.getFermentation_phases());
            fermentAdapter.notifyDataSetChanged();
            hopAdapter.clear();
            hopAdapter.addAll(currentRecipe.getRecipe_hops());
            hopAdapter.notifyDataSetChanged();
        }

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
