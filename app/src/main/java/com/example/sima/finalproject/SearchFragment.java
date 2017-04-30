package com.example.sima.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    MyClickListenerFromListFragment mListener;
    ProgressDialog dialog;
    //JsonExtractor jsonExtractor;
    CustomListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_search, container, false);

       //(ListView) ((ResultFragment)getFragmentManager()).getView().findViewById(R.id.listView);
       Global.setJsonExtractor(new JsonExtractor((ListView) view.findViewById(R.id.listView)));

        // Spinner element
        Spinner spinner = (Spinner) view.findViewById(R.id.networks_spinner);
        final ArrayAdapter<CharSequence> spnrAdapter = ArrayAdapter.createFromResource(view.getContext(),
        R.array.networks_array, android.R.layout.simple_spinner_item);
        spnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spnrAdapter);

        // Spinner element- Genres
        Spinner spinnerGenres = (Spinner) view.findViewById(R.id.genre_spinner);
        final ArrayAdapter<CharSequence> spnrAdapterGenres =
                ArrayAdapter.createFromResource(view.getContext(),
                        R.array.genres_array, android.R.layout.simple_spinner_item);
        spnrAdapterGenres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenres.setAdapter(spnrAdapterGenres);

        //components
        final EditText etQuery = (EditText) view.findViewById(R.id.etQuery);
        final Button btnFind =(Button) view.findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.getJsonExtractor().setShows(new ArrayList<Show>());
                adapter = new CustomListAdapter(getActivity(),Global.getJsonExtractor().getShows());
                Global.getJsonExtractor().setAdapter(adapter);
               // jsonExtractor.setListViewShows();
                Global.setEpisodeList(false);
                Global.setStrQuery(etQuery.getText().toString());

                try{
                    dialog= ProgressDialog.show(getActivity(),
                            Constant.LOAD_MSG,
                            Constant.WAIT_MSG,
                            true);
                    String tmpUrl = Constant.URL_SHOWS + etQuery.getText().toString();

                    // get Json for show inserted by the user
                    Global.getJsonExtractor().getJsonShows(tmpUrl,getContext());
                    dialog.dismiss();
                }
                catch(Exception e){
                    dialog.dismiss();
                    Toast.makeText(getActivity(),
                            Constant.ERR_MSG + e.toString(),
                            Toast.LENGTH_LONG).show();
                }

                mListener.onButtonClickFind(v);
            }
        });

        TextView tvMyZone=(TextView)view.findViewById(R.id.tvMyZone);
        tvMyZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMyZoneClick(view);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (MyClickListenerFromListFragment) context;
        }
        catch(ClassCastException e){
            Toast.makeText(context,"error", Toast.LENGTH_LONG).show();
        }
    }
}
