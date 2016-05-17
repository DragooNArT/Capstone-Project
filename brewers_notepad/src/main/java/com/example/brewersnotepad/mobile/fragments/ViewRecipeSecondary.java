package com.example.brewersnotepad.mobile.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.example.brewersnotepad.mobile.json.JsonUtility;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;
import com.example.brewersnotepad.mobile.providers.RecipeStorageProvider;


public class ViewRecipeSecondary extends ViewBaseFragment {

    private OnFragmentInteractionListener mListener;
    private  ViewHopListAdapter<HopEntry> hopAdapter;
    private ViewFermentListAdapter<FermentationEntry> fermentAdapter;
    public ViewRecipeSecondary() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_recipe_secondary, container, false);
        ListView fermentList = (ListView) view.findViewById(R.id.viewFermentList);
        fermentAdapter = new ViewFermentListAdapter<>(getContext(), android.R.layout.simple_list_item_1, mMetricsProvider, fermentList);
        fermentList.setAdapter(fermentAdapter);

        ListView hopList = (ListView) view.findViewById(R.id.viewHopList);
        hopAdapter = new ViewHopListAdapter<>(getContext(), android.R.layout.simple_list_item_1, mMetricsProvider, hopList);
        hopList.setAdapter(hopAdapter);
//        fill_In_View(view, fermentAdapter, hopAdapter);
        return view;
    }

    private void fill_In_View() {
        if (currentRecipe != null) {
            TextView hopDurationView = (TextView) getView().findViewById(R.id.viewHopPhaseDurationInput);
            if (currentRecipe.getHopSteepDuration() > 0) {
                hopDurationView.setText(mMetricsProvider.convertMinsToText(currentRecipe.getHopSteepDuration()));
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

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.isAfterLast()) {
            data.moveToFirst();
            String recipe_data = data.getString(data.getColumnIndex(RecipeStorageProvider.FIELD_RECIPE_DATA));
            if(recipe_data != null) {
                currentRecipe = JsonUtility.JsonToObject(recipe_data);
                fill_In_View();
            }
        }
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
