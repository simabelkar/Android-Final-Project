package com.example.sima.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements MyClickListenerFromListFragment {

    int orientation;
    Fragment fragment, frgSearch, frgResults,mCurrentFragment,mFragmentContainer,mStackFragments ;
    FragmentManager fm;
    CustomListAdapterEpisodes adapterEpisodes;
    ProgressDialog dialog;
    AlarmReciever alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //save the current orientation:
     //   orientation= getResources().getConfiguration().orientation;

        fm = getSupportFragmentManager();
        mFragmentContainer=fm.findFragmentById(R.id.fragmentContainer);

        frgSearch = new SearchFragment();
        frgResults = new ResultFragment();

        //mStackFragments = new stack();

       // if (orientation == getResources().getConfiguration().orientation.)
        FragmentTransaction trans = fm.beginTransaction();
        /*trans.add(android.R.id.content,frgResults,"frgResults");
        trans.hide(frgResults);*/

        trans.add(android.R.id.content,frgSearch,"frgSearch");
        trans.commit();

       /* mCurrentFragment = frgSearch;*/

    }

    //btnFind of fragment Search
    //btnBack of fragment Results
    @Override
    public void onButtonClickFind(View view) {

//        Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());

        InputMethodManager imm = (InputMethodManager)getSystemService
                (Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(android.R.id.content, frgResults, "frgResults");
        trans.commit();

//        Global.SetVisibilityShowDetails(View.GONE);

       // Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());

        //shows adapter:
        Global.getJsonExtractor().getListViewShows().
                setAdapter(Global.getJsonExtractor().getAdapter());



    }

    @Override
    public void onButtonClickBack(View view) {

        if (Global.isEpisodeList()){

               // frgSearch = new ResultFragment();
                Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());

                //shows adapter:
                Global.getJsonExtractor().getListViewShows().
                        setAdapter(Global.getJsonExtractor().getAdapter());

                Global.setEpisodeList(false);

                FragmentTransaction trans = fm.beginTransaction();
                trans.replace(android.R.id.content, frgResults, "frgResults");
                trans.commit();
            }
        else{
            Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());
            Global.SetVisibilityShowDetails(View.GONE);
            Global.setEpisodeList(false);

            FragmentTransaction trans = fm.beginTransaction();
            trans.replace(android.R.id.content, frgSearch, "frgSearch");
            trans.commit();
        }

    }

       /*// Toast.makeText(this,mCurrentFragment.toString(), Toast.LENGTH_LONG).show();
        if (mCurrentFragment==frgSearch) {//Find Button

            //close keyboard after clicking button:
            InputMethodManager imm = (InputMethodManager)getSystemService
                    (Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            Global.SetVisibilityShowDetails(View.GONE);

            Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());

            //shows adapter:
            Global.getJsonExtractor().getListViewShows().
                    setAdapter(Global.getJsonExtractor().getAdapter());


            showFragment(frgResults);
        }

        else if (mCurrentFragment==frgResults){
            //  Global.getTvTitleResults().setText("Episodes for Show:" + Global.getStrQuery());

            if (Global.isEpisodeList()){

                //preperation for list of show
                Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());

                Global.SetVisibilityShowDetails(View.GONE);

                //shows adapter:
                Global.getJsonExtractor().getListViewShows().
                        setAdapter(Global.getJsonExtractor().getAdapter());

                Global.setEpisodeList(false);
                showFragment(frgResults);
            }
            else{
                //preperation for Search
                Global.getTvTitleResults().setText("Search result for:" + Global.getStrQuery());
                Global.SetVisibilityShowDetails(View.GONE);
                Global.setEpisodeList(false);
                showFragment(frgSearch);
            }
        }

    }*/

    @Override
    public void onItemClick(View view,int position) {

       /* if (Global.isEpisodeList()) return; //nothing to do when clicking on item of episodes list
        Show show = Global.getJsonExtractor().getShows().get(position);//get the position in the list to take the Show id
        Global.getJsonExtractor().setEpisodes(new ArrayList<Episode>());
        adapterEpisodes = new CustomListAdapterEpisodes(MainActivity.this,Global.getJsonExtractor().getEpisodes());
        Global.getJsonExtractor().setAdapterEpisode(adapterEpisodes);

        try{
            dialog= ProgressDialog.show(MainActivity.this,
                    Constant.LOAD_MSG,
                    Constant.WAIT_MSG,
                    true);
            String tmpEpisodesUrl = Constant.URL_EPISODES.replace("#", show.getIdShow()) ;
            Global.setEpisodeList(true);
            Global.getJsonExtractor().getJsonEpisodes(tmpEpisodesUrl,this); // get Json for all episodes of the show chosen by the user
            (Global.getJsonExtractor().getAdapterEpisode()).notifyDataSetChanged();
            dialog.dismiss();
        }
        catch(Exception e){
            dialog.dismiss();
            Toast.makeText(MainActivity.this,
                    Constant.ERR_MSG + e.toString(),
                    Toast.LENGTH_LONG).show();
        }*/

        Global.SetVisibilityShowDetails(View.VISIBLE);
        Global.getTvTitleResults().setText("Episodes for Show:" + Global.getStrChosenShow());


        //Episodes adapter:
        Global.getJsonExtractor().getListViewEpisodes().
                setAdapter(Global.getJsonExtractor().getAdapterEpisode());

       // showFragment(frgSearch);

    }

    @Override
    public void onFavoriteClick(Context context, String show_id, String show_name,boolean isFavorite) {

        //insert the show_id, show_title to the table
        if(isFavorite)
            AssingmentsDBHelper.InsertFavorite(context,show_id,show_name);
        //remove the show_id from the table
        else
            AssingmentsDBHelper.DeleteFavorite(context,show_id);
    }

    @Override
    public void onScheduleClick(Context context,String show_id, String show_name,String show_time, boolean isScheduled ) {
        //insert the show_id, show_title to the table
        if(isScheduled) {
            AssingmentsDBHelper.InsertSchedule(context, show_id, show_name, show_time);
            setTimeForAlarm(show_name, show_time);
        }
        else
            AssingmentsDBHelper.DeleteSchedule(context,show_id);

    }

    @Override
    public void onMyZoneClick(View view) {
        Intent intent = new Intent (MainActivity.this,myZone.class);
        startActivity(intent);
    }



    @Override
    public void onDialogResponse(boolean activateDialog, String err){
        if(activateDialog){
            dialog= ProgressDialog.show(this,
                    Constant.LOAD_MSG,
                    Constant.WAIT_MSG,
                    true);
            //dialog.show();
        }

        else{
            if(err.toString().length()>0)
                Toast.makeText(MainActivity.this,
                        err,
                        Toast.LENGTH_LONG).show();

            dialog.dismiss();
        }
    }

    @Override
    public void makeToast(String msg){
        Toast.makeText(MainActivity.this,
                msg,
                Toast.LENGTH_LONG).show();
    }

    //********************************************************************************

    private void showFragment (Fragment fragment)
    {
        /*if (fragment.isVisible()){
            return;
        }*/
        FragmentTransaction trans = fm.beginTransaction();
        fragment.getView().bringToFront();
        mCurrentFragment.getView().bringToFront();

        trans.hide(mCurrentFragment);
        trans.show(fragment);

      //  trans.addToBackStack(null);
       // mStackFragments.Push(mCurrentFragment);
        trans.commit();

        mCurrentFragment = fragment;
    }



    public void setTimeForAlarm(String showName, String showTime){

        long nowUtc=System.currentTimeMillis();
        //int offsetGtm = countriesAndFlags.getGtm()[savePosition];   //get the offset gtm of the country
       /* String gmt = "GMT" + (offsetGtm < 0 ? "":"+") + offsetGtm;
        mCalendar = new GregorianCalendar(TimeZone.getTimeZone(gmt));
        mCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
        mCalendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());*/

     //   long milliesForTiming = mCalendar.getTimeInMillis();
        /*if(milliesForTiming < nowUtc){
            mCalendar.add(Calendar.DAY_OF_MONTH, 1);// add 1 day
        }*/
      //  alarm.setAlarm(MainActivity.this, milliesForTiming);
    }
}
