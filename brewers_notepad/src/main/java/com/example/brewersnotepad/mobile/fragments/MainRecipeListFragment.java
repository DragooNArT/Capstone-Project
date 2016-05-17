package com.example.brewersnotepad.mobile.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brewersnotepad.R;
import com.example.brewersnotepad.mobile.adapters.RecipeListAdapter;
import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.example.brewersnotepad.mobile.json.JsonUtility;
import com.example.brewersnotepad.mobile.listeners.MainActivityFabListener;
import com.example.brewersnotepad.mobile.providers.RecipeRuntimeManager;
import com.example.brewersnotepad.mobile.providers.RecipeStorageProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainRecipeListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainRecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainRecipeListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
     private static final int LOADER_ID = 999;
    private RecyclerView mBeerListView;
    private RecipeListAdapter mBeerListAdapter;
    public static final int FRAGMENT_ID = 3;

    private OnFragmentInteractionListener mListener;

    public MainRecipeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainRecipeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainRecipeListFragment newInstance(String param1, String param2) {
        MainRecipeListFragment fragment = new MainRecipeListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null,this);
    }

    public void loadData(Cursor data) {
       if(data != null) {
               data.moveToFirst();
               List<RecipeDataHolder> recipesList = new ArrayList<RecipeDataHolder>();
                   while (!data.isAfterLast()) {
                       String recipe_id = data.getString(data.getColumnIndex(RecipeStorageProvider.FIELD_RECIPE_NAME));
                       String recipe_data = data.getString(data.getColumnIndex(RecipeStorageProvider.FIELD_RECIPE_DATA));
                       RecipeDataHolder entry = JsonUtility.JsonToObject(recipe_data);
                       if(entry != null) {
                           recipesList.add(entry);
                       }
                       data.moveToNext();
                   }
               RecipeRuntimeManager.getRecipesList().clear();
               RecipeRuntimeManager.getRecipesList().addAll(recipesList);
                mBeerListAdapter.notifyDataSetChanged();
        } else {
           RecipeRuntimeManager.getRecipesList().clear();
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe_list, container, false);
        if(getActivity().findViewById(R.id.search)!=null)
        getActivity().findViewById(R.id.search).setVisibility(View.VISIBLE);
        mBeerListView = (RecyclerView) view.findViewById(R.id.beer_recipe_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBeerListView.setLayoutManager(layoutManager);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mBeerListView.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                promptDeleteItem(viewHolder);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mBeerListView);
        // The ForecastAdapter will take data from a source and
        // use it to populate the RecyclerView it's attached to.
        mBeerListAdapter = new RecipeListAdapter(getActivity(),view.findViewById(R.id.recipe_name));

        mBeerListView.setAdapter(mBeerListAdapter);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.addRecipeFab);
        MainActivityFabListener listener = new MainActivityFabListener(getActivity());
        fab.setOnClickListener(listener);

        return view;
    }

    private void promptDeleteItem(final RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getString(R.string.delete_swipe_prompt_title));
        LayoutInflater inflater = getLayoutInflater(null);
        View dialogLayout = inflater.inflate(R.layout.prompt_content,null);
        int pos = viewHolder.getLayoutPosition();
        RecipeDataHolder recipeHolder = RecipeRuntimeManager.getRecipesList().get(pos);
        TextView text = (TextView)dialogLayout.findViewById(R.id.prompt_text);
        text.setText(getActivity().getString(R.string.prompt_delete_content_text,recipeHolder.getRecipe_name()));

        builder.setView(dialogLayout);
        builder.setPositiveButton(getContext().getString(R.string.delete_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(viewHolder);
            }
        });
        builder.setNegativeButton(getContext().getString(R.string.cancel_button_text), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mBeerListAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void deleteItem(RecyclerView.ViewHolder viewHolder) {
        int pos = viewHolder.getLayoutPosition();
        RecipeDataHolder recipeHolder = RecipeRuntimeManager.getRecipesList().get(pos);
        if(recipeHolder != null) {
            int id = getActivity().getContentResolver().delete(RecipeStorageProvider.CONTENT_URI, recipeHolder.getRecipe_name(), null);

            if(id != 0) {
                RecipeRuntimeManager.getRecipesList().remove(recipeHolder);
                mBeerListAdapter.notifyItemRemoved(pos);
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

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] columns = new String[]{RecipeStorageProvider.FIELD_RECIPE_NAME,RecipeStorageProvider.FIELD_RECIPE_DATA};
        return new CursorLoader(getContext(),RecipeStorageProvider.CONTENT_URI, columns,null,null,null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        loadData(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

        loadData(null);
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
