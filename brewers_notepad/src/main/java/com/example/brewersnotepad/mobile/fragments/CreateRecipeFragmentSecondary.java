package com.example.brewersnotepad.mobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.FermentListAdapter;
import com.example.brewersnotepad.mobile.adapters.GrainListAdapter;
import com.example.brewersnotepad.mobile.adapters.HopListAdapter;
import com.example.brewersnotepad.mobile.data.FermentationEntry;
import com.example.brewersnotepad.mobile.data.GrainEntry;
import com.example.brewersnotepad.mobile.data.HopEntry;
import com.example.brewersnotepad.mobile.listeners.AddFermentListener;
import com.example.brewersnotepad.mobile.listeners.AddHopsListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateRecipeFragmentSecondary.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateRecipeFragmentSecondary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRecipeFragmentSecondary extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  HopListAdapter<HopEntry> hopAdapter;
    private FermentListAdapter<FermentationEntry> fermentAdapter;
    private AddHopsListener addHopsListener;
    private AddFermentListener addFermentListener;
    private OnFragmentInteractionListener mListener;

    public CreateRecipeFragmentSecondary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateRecipeFragmentSecondary.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRecipeFragmentSecondary newInstance(String param1, String param2) {
        CreateRecipeFragmentSecondary fragment = new CreateRecipeFragmentSecondary();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_recepie_fragment_secondary, container, false);

        ListView hopsList = (ListView)view.findViewById(R.id.hopsListView);
        hopAdapter = new HopListAdapter<HopEntry>(getContext(),
                android.R.layout.simple_list_item_1);
        hopsList.setAdapter(hopAdapter);
        addHopsListener = new AddHopsListener(hopAdapter,view);

        ImageButton addHopsButton = (ImageButton)view.findViewById(R.id.addHopsButton);
        addHopsButton.setOnClickListener(addHopsListener);

        ListView fermentList = (ListView)view.findViewById(R.id.fermentListView);
        fermentAdapter = new FermentListAdapter<FermentationEntry>(getContext(),
                android.R.layout.simple_list_item_1);
        fermentList.setAdapter(fermentAdapter);
        addFermentListener = new AddFermentListener(fermentAdapter,view);

        ImageButton addFermentButton = (ImageButton)view.findViewById(R.id.addFermentButton);
        addFermentButton.setOnClickListener(addFermentListener);
        return view;
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
