package com.example.sima.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment {

    MyClickListenerFromListFragment mListener;
    ProgressDialog dialog;
    CustomListAdapterEpisodes adapter;
    Show show;

    //database
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_result, container, false);

        Global.setTvTitleResults((TextView)view.findViewById(R.id.tvTitle));
        Global.setTvGnr((TextView)view.findViewById(R.id.tvGenreTitle));
        Global.setTvGnrVal((TextView)view.findViewById(R.id.tvGenreVal));
        Global.setTvYr((TextView)view.findViewById(R.id.tvYearTitle));
        Global.setTvYrVal((TextView)view.findViewById(R.id.tvYearVal));
        Global.setTvSch((TextView)view.findViewById(R.id.tvScheduleTitle));
        Global.setTvSchVal((TextView)view.findViewById(R.id.tvScheduleVal));
        Global.setTvNw((TextView)view.findViewById(R.id.tvNetTitle));
        Global.setTvNwVal((TextView)view.findViewById(R.id.tvNetVal));
        Global.setRtb((RatingBar) view.findViewById(R.id.ratingBar));
        Global.setSwtFav((Switch) view.findViewById(R.id.switch1));

        /*final RatingBar ratingBar=(RatingBar)view.findViewById(R.id.ratingBar);
        final Switch switch=(Switch)view.findViewById(R.id.switch1);*/

        /*if(Global.isEpisodeList())
            tv.setText("Search result for:" + Global.getStrQuery());
        else tv.setText("Episodes for Show:" + Global.getStrQuery());*/

        Global.setJsonExtractor(
                new JsonExtractor((ListView) view.findViewById(R.id.listView)));
        final ListView listView = (ListView) view.findViewById(R.id.listView);

        //shows adapter:
        Global.getJsonExtractor().getListViewShows().
                setAdapter(Global.getJsonExtractor().getAdapter());
        //listView.setAdapter(adapter);

     //   Global.getJsonExtractor().getAdapterEpisode().notifyDataSetChanged();

        //listView Listener
       Global.getJsonExtractor().getListViewShows().
        //listView.
                setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){

                if (Global.isEpisodeList()) return; //nothing to do when clicking on item of episodes list

                Show show = Global.getJsonExtractor().getShows().get(position);

                //main info of Show:
                Global.setStrChosenShow(show.getTitle());
                Global.setIdChosenShow(show.getIdShow());
                Global.getTvYrVal().setText(show.getYearPremiered().toString().substring(0,4));
                Global.getTvNwVal().setText(show.getNetwork() );
                String tmp="";
                for (int j = 0; j < show.getGenres().size(); j++)
                    tmp+=show.getGenresByIndex(j)+" | ";
                Global.getTvGnrVal().setText(tmp);
                tmp="";
                for (int j = 0; j < show.getScheduleDays().size(); j++)
                    tmp+=show.getScheduleDaysByIndex(j)+" | ";
                Global.getTvSchVal().setText(tmp+" "+ show.getScheduleTime());
                Global.setTimeChosenShow(Global.getTvSchVal().getText().toString());
                 //////////////////


                Global.getJsonExtractor().setEpisodes(new ArrayList<Episode>());
                adapter = new CustomListAdapterEpisodes(getActivity(),Global.getJsonExtractor().getEpisodes());
               // listView.setAdapter(adapter);
                Global.getJsonExtractor().setAdapterEpisode(adapter);

                try{
                    dialog= ProgressDialog.show(getActivity(),
                            Constant.LOAD_MSG,
                            Constant.WAIT_MSG,
                            true);
                    String tmpEpisodesUrl = Constant.URL_EPISODES.replace("#", show.getIdShow()) ;
                    Global.setEpisodeList(true);
                    Global.getJsonExtractor().getJsonEpisodes(tmpEpisodesUrl,getContext());// get Json for all episodes of the show chosen by the user
                    dialog.dismiss();

                    adapter.notifyDataSetChanged();
                }
                catch(Exception e){
                    dialog.dismiss();
                    Toast.makeText(getActivity(),
                            Constant.ERR_MSG + e.toString(),
                            Toast.LENGTH_LONG).show();
                }

                mListener.onItemClick(view,position);
            }
        });


        final Button btnFind =(Button) view.findViewById(R.id.btnBack);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClickBack(v);
            }
        });

        Global.getRtb().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                 @Override
                 public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                     if (rating==1)
                         mListener.onFavoriteClick(getContext(),Global.getIdChosenShow(),Global.getStrChosenShow(),true);
                     else
                         mListener.onFavoriteClick(getContext(),Global.getIdChosenShow(),"",false);

                 }
             }
        );

        Global.getSwtFav().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    mListener.onScheduleClick(getContext(),Global.getIdChosenShow(),Global.getStrChosenShow(),Global.getTimeChosenShow(),true);
                else
                    mListener.onScheduleClick(getContext(),Global.getIdChosenShow(),"","",false);
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
