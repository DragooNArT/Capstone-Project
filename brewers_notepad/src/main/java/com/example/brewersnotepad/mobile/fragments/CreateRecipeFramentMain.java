package com.example.brewersnotepad.mobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.activities.CreateRecipeActivity;
import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.adapters.ViewGrainListAdapter;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.listeners.AddGrainListener;
import com.example.brewersnotepad.mobile.listeners.PreferencesChangedListener;
import com.example.brewersnotepad.mobile.providers.MetricsProvider;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;

import java.util.ArrayList;
import java.util.List;


public class CreateRecipeFramentMain extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public static final String GRAIN_PARCELABLE = "Grain_parcel";
    private GrainListAdapter<GrainEntry> adapter;
    private MetricsProvider mMetricsProvider;
    private boolean isNewRecipe;

    private OnFragmentInteractionListener mListener;
    private AddGrainListener addGrainListener;
    public CreateRecipeFramentMain() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mMetricsProvider = new MetricsProvider(getContext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        ArrayList<GrainEntry> list = new ArrayList<GrainEntry>();
        for(int i=0;i<adapter.getCount();i++) {
            list.add(adapter.getItem(i));
        }

        outState.putParcelableArrayList(GRAIN_PARCELABLE,list);
        outState.putBoolean(CreateRecipeActivity.NEW_RECIPE_KEY,isNewRecipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_recipe_frament1, container, false);
        Spinner recipe_type_spinner = (Spinner)view.findViewById(R.id.recipe_type_spinner);
        ArrayAdapter<CharSequence> recipe_type_adapter = ArrayAdapter.createFromResource(getContext(),R.array.recipe_types_list,R.layout.simple_spinner_item);

        recipe_type_spinner.setAdapter(recipe_type_adapter);
        ListView grainList = (ListView)view.findViewById(R.id.grain_list);


        adapter = new GrainListAdapter<GrainEntry>(getContext(),
                android.R.layout.simple_list_item_1,grainList);
        if(savedInstanceState == null) {
            savedInstanceState = getArguments();
        }
        if(savedInstanceState != null) {
            ArrayList<GrainEntry> list = savedInstanceState.getParcelableArrayList(GRAIN_PARCELABLE);
            if(list != null) {
                adapter.addAll(list);
            }
        }
        grainList.setAdapter(adapter);


        ImageButton addGrainButton = (ImageButton)view.findViewById(R.id.addGrainButton);
        addGrainListener = new AddGrainListener(adapter,view);
        addGrainButton.setOnClickListener(addGrainListener);
        fillData(view,recipe_type_adapter);

        if(savedInstanceState != null) {
            isNewRecipe = savedInstanceState.getBoolean(CreateRecipeActivity.NEW_RECIPE_KEY,true);
            view.findViewById(R.id.inputRecipeName).setEnabled(isNewRecipe);
        }
        return view;
    }

    private void fillData(View view, ArrayAdapter<CharSequence> recipe_type_adapter ) {
        RecipeDataHolder currentRecipe = RecipeRuntimeManager.getCurrentRecipe();
        if(currentRecipe != null) {

            if(currentRecipe.getRecipe_name()!=null) {
                TextView recipeName = (TextView) view.findViewById(R.id.inputRecipeName);
                recipeName.setText(currentRecipe.getRecipe_name());
            }
            if(currentRecipe.getRecipe_type()!=null) {
                Spinner recipeType = (Spinner) view.findViewById(R.id.recipe_type_spinner);
                int pos = recipe_type_adapter.getPosition(currentRecipe.getRecipe_type());
                recipeType.setSelection(pos);
            }
            if(currentRecipe.getMashDuration()>0) {
                TextView mashDuration = (TextView) view.findViewById(R.id.inputMashDuration);
                mashDuration.setText(currentRecipe.getMashDuration()+getString(R.string.time_in_minutes));
            }
            if(currentRecipe.getMashTemp()>0) {
                TextView mashTemp = (TextView) view.findViewById(R.id.mashTempInput);
                mashTemp.setText(mMetricsProvider.getTempToString(currentRecipe.getMashTemp()));
            }




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
