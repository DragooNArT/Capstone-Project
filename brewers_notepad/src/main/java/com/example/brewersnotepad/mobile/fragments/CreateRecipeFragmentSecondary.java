package com.example.brewersnotepad.mobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.FermentListAdapter;
import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.adapters.HopListAdapter;
import com.example.brewersnotepad.mobile.data.FermentationEntry;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.listeners.AddFermentListener;
import com.example.brewersnotepad.mobile.listeners.AddHopsListener;
import com.example.brewersnotepad.mobile.listeners.SuffixTextWatcher;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateRecipeFragmentSecondary.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CreateRecipeFragmentSecondary extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String HOP_PARCELABLE = "Hop_parcel";
    private static final String FERMENT_PARCELABLE = "Ferment_parcel";

    // TODO: Rename and change types of parameters
    private HopListAdapter<HopEntry> hopAdapter;
    private FermentListAdapter<FermentationEntry> fermentAdapter;
    private AddHopsListener addHopsListener;
    private AddFermentListener addFermentListener;
    private OnFragmentInteractionListener mListener;
    private MetricsProvider mMetricsProvider;
    public CreateRecipeFragmentSecondary() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMetricsProvider = new MetricsProvider(getContext());

    }

    private ArrayList getListFromAdapter(ListAdapter adapter) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < adapter.getCount(); i++) {
            list.add(adapter.getItem(i));
        }
        return list;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(HOP_PARCELABLE, getListFromAdapter(hopAdapter));
        outState.putParcelableArrayList(FERMENT_PARCELABLE, getListFromAdapter(fermentAdapter));
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_recepie_fragment_secondary, container, false);

        ListView hopsList = (ListView) view.findViewById(R.id.hopsListView);
        hopAdapter = new HopListAdapter<HopEntry>(getContext(),
                android.R.layout.simple_list_item_1, hopsList);

        addHopsListener = new AddHopsListener(hopAdapter,view,mMetricsProvider);

        ImageButton addHopsButton = (ImageButton) view.findViewById(R.id.addHopsButton);
        addHopsButton.setOnClickListener(addHopsListener);

        ListView fermentList = (ListView) view.findViewById(R.id.fermentListView);
        fermentAdapter = new FermentListAdapter<FermentationEntry>(getContext(),
                android.R.layout.simple_list_item_1, fermentList,mMetricsProvider);

        addFermentListener = new AddFermentListener(fermentAdapter, view,mMetricsProvider);
        if (savedInstanceState != null) {
            ArrayList<HopEntry> hopList = savedInstanceState.getParcelableArrayList(HOP_PARCELABLE);
            hopAdapter.addAll(hopList);
            ArrayList<FermentationEntry> fermList = savedInstanceState.getParcelableArrayList(FERMENT_PARCELABLE);
            fermentAdapter.addAll(fermList);
        }
        fermentList.setAdapter(fermentAdapter);
        hopsList.setAdapter(hopAdapter);
        ImageButton addFermentButton = (ImageButton) view.findViewById(R.id.addFermentButton);
        addFermentButton.setOnClickListener(addFermentListener);
        setSuffixListeners(view);
        fillData(view,hopAdapter,fermentAdapter);
        return view;
    }

    private void setSuffixListeners(View view) {
        EditText hopPhaseInput = (EditText)view.findViewById(R.id.hopPhaseDurationInput);
        hopPhaseInput.addTextChangedListener(new SuffixTextWatcher(hopPhaseInput,getString(R.string.time_in_minutes)));

        EditText hopQuantityInput = (EditText)view.findViewById(R.id.hopQuantityInput);
        hopQuantityInput.addTextChangedListener(new SuffixTextWatcher(hopQuantityInput,mMetricsProvider.getSmallWeightPrefix()));

        EditText hopTimeAddInput = (EditText)view.findViewById(R.id.hopTimeAddInput);
        hopTimeAddInput.addTextChangedListener(new SuffixTextWatcher(hopTimeAddInput,getString(R.string.time_in_minutes)));

        EditText fermentTemp = (EditText)view.findViewById(R.id.fermentTemp);
        fermentTemp.addTextChangedListener(new SuffixTextWatcher(fermentTemp,mMetricsProvider.getTempPrefix()));

        EditText fermentTime = (EditText)view.findViewById(R.id.fermentTime);
        fermentTime.addTextChangedListener(new SuffixTextWatcher(fermentTime,getString(R.string.time_in_days)));

    }

    private void fillData(View view, HopListAdapter<HopEntry> hopAdapter, FermentListAdapter<FermentationEntry> fermentAdapter) {
        RecipeDataHolder currentRecipe = RecipeRuntimeManager.getCurrentRecipe();
        if (currentRecipe != null) {
            TextView hopDurationInput = (TextView)view.findViewById(R.id.hopPhaseDurationInput);
            if (currentRecipe.getHopSteepDuration() > 0) {
                hopDurationInput.setText(mMetricsProvider.convertMinsToText(currentRecipe.getHopSteepDuration()));
            }
            hopAdapter.clear();
            hopAdapter.addAll(currentRecipe.getRecipe_hops());
            hopAdapter.notifyDataSetChanged();
            fermentAdapter.clear();
            fermentAdapter.addAll(currentRecipe.getFermentation_phases());
            fermentAdapter.notifyDataSetChanged();
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
