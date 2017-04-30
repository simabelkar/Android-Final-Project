package com.example.sima.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SelectedShowActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private List<Episode> episodes;
    private CustomListAdapterEpisodes adapter;
    private ListView listView;
    private String showID, title,schedule;

    private AlarmReciever alarmReciever;

    AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(this);
    SQLiteDatabase db;
    CursorAdapterFav cursorAdapterFavorite;
    CursorAdapterSchdl cursorAdapterSchedule;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_show);

        Intent intent = getIntent();

        db = dbHelper.getReadableDatabase();
        Cursor cFavorite = db.query(
                Constant.Shows.TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterFavorite = new CursorAdapterFav(this,cFavorite);
        Cursor cSchedule = db.query(
                Constant.Shows.TABLE_SCHEDULE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterSchedule = new CursorAdapterSchdl(this,cSchedule);

        //alarm:
        alarmReciever = new AlarmReciever();

        //main info of Show:
        showID=intent.getStringExtra("id");

        title=intent.getStringExtra("title");
        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        TextView tvYear = (TextView)findViewById(R.id.tvYearVal);
        tvYear.setText(intent.getStringExtra("year"));

        TextView tvNw = (TextView)findViewById(R.id.tvNetVal);
        tvNw.setText(intent.getStringExtra("nw"));

        TextView tvGenre = (TextView)findViewById(R.id.tvGenreVal);
        tvGenre.setText(intent.getStringExtra("genre"));

        schedule=intent.getStringExtra("schedule");
        TextView tvSchd = (TextView)findViewById(R.id.tvScheduleVal);
        tvSchd.setText(schedule);



        episodes = new ArrayList<Episode>();
        adapter = new CustomListAdapterEpisodes(SelectedShowActivity.this,episodes);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


        try{
            dialog= ProgressDialog.show(this,
                    Constant.LOAD_MSG,
                    Constant.WAIT_MSG,
                    true);
            //jsonExtractor.
            String tmpEpisodesUrl = Constant.URL_EPISODES.replace("#", showID) ;

            getJsonEpisodes(tmpEpisodesUrl);
        }
        catch(Exception e){
            dialog.dismiss();
            Toast.makeText(this,
                    Constant.ERR_MSG + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

       RatingBar rtb= (RatingBar) findViewById(R.id.ratingBar);
        if(dbHelper.isFavoriteShowExist(showID)){
            rtb.setRating(1);
        }
        rtb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 if (rating==1)
                     onFavoriteClick(SelectedShowActivity.this,
                             showID,title,true);
                 else
                     onFavoriteClick(SelectedShowActivity.this,
                             showID,"",false);

             }
         }
        );

        Switch swtch = (Switch)findViewById(R.id.switch1);
        if(dbHelper.isScheduleShowExist(showID)){
            swtch.setChecked(true);
        }
        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    onScheduleClick(SelectedShowActivity.this,
                            showID,title,schedule,true);
                else
                    onScheduleClick(SelectedShowActivity.this,
                            showID,"","",false);
            }
        });

        TextView tvMyZone=(TextView)findViewById(R.id.tvMyZone);
        tvMyZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent
                        (SelectedShowActivity.this,myZone.class);
                //    intent.putExtra("QUERY", strQuery);
                startActivity(intent);
            }
        });

    }

    //************************** favorite & schedule: DB *************************************************
    public void onFavoriteClick(Context context,String show_id, String show_name, boolean isFavorite)
    {
        //insert the show_id, show_title to the table
        if(isFavorite)
            AssingmentsDBHelper.InsertFavorite(context,show_id,show_name);

            //remove the show_id from the table
        else
            AssingmentsDBHelper.DeleteFavorite(context,show_id);

        //update cursor
        db = dbHelper.getReadableDatabase();
        Cursor newcFavorite = db.query(
                Constant.Shows.TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterFavorite.changeCursor(newcFavorite);
        cursorAdapterFavorite.notifyDataSetChanged();
    }

    public void onScheduleClick(Context context, String show_id, String show_name, String show_time, boolean isScheduled)
    {
        //insert the show_id, show_title, show_time to the table
        if(isScheduled) {
            AssingmentsDBHelper.InsertSchedule(context, show_id, show_name, show_time);

            //setAlarm weekly:
            //current
            Calendar calendar=Calendar.getInstance();
            int currentDay= calendar.get(Calendar.DAY_OF_WEEK);
            int currentHour= calendar.get(Calendar.HOUR_OF_DAY);
            long currentUTC=System.currentTimeMillis();//current time

            //schedule day(s)
            show_time=show_time.replace(" ","");
            show_time=show_time.replace("|",",");
            String[] daysSchedule= show_time.split(",");;//getDays(show_time);

           // show_time = show_time.substring(0, show_time.lastIndexOf("|")-1);
           // int indexTime = show_time.lastIndexOf("|")+1;
            String[] scheduleTime= daysSchedule [daysSchedule.length-1].split(":"); //show_time.substring(indexTime).split(":") ;
            int scheduleHour= Integer.parseInt(scheduleTime[0]);
            int scheduleMinute= Integer.parseInt(scheduleTime[1]);

            Calendar calendarSet=Calendar.getInstance();
            long milliesForTiming;

            for(int i= 0; i< daysSchedule.length -1;i++) //-1: the last is time
            {
                int scheduleDay = getIntDay(daysSchedule[i]);
                //crnt day == scheduleDay, setAlarm(time only) => hour, minute
                if(currentDay== scheduleDay)//same day as today
                {
                    calendarSet.set(Calendar.DAY_OF_WEEK, currentDay);

                }
                //if scheduleDay > crnt: day= scheduleDay minus crnt
                //if scheduleDay < crnt: day=(7 minus crnt day )+ scheduleDay
                else
                {
                    if (scheduleDay > currentDay){
                        calendarSet.set(Calendar.DAY_OF_WEEK, (scheduleDay-currentDay));
                    }
                    if (scheduleDay < currentDay){
                        calendarSet.set(Calendar.DAY_OF_WEEK, (7-currentDay)+scheduleDay);
                    }
                }
                calendarSet.set(Calendar.HOUR_OF_DAY, scheduleHour);
                calendarSet.set(Calendar.MINUTE, scheduleMinute);
                calendarSet.set(Calendar.SECOND, 0);
                calendarSet.set(Calendar.AM_PM, Calendar.AM );

                //convert day to milliseconds
                //day+nowUTC +hour => setAlarm()
                milliesForTiming = calendarSet.getTimeInMillis();
                alarmReciever.setAlarm(this, milliesForTiming);
            }

        }

        else //remove the show_id from the table
            AssingmentsDBHelper.DeleteSchedule(context,show_id);

        //update cursor
        db = dbHelper.getReadableDatabase();
        Cursor newcSchedule = db.query(
                Constant.Shows.TABLE_SCHEDULE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterSchedule.changeCursor(newcSchedule);
        cursorAdapterSchedule.notifyDataSetChanged();
    }

    private int getIntDay(String day) {

        switch (day) {
            case "Sunday":
                return 1;
            case "Monday":
                return 2;
            case "Tuesday":
                return 3;
            case "Wednesday":
                return 4;
            case "Thursday":
                return 5;
            case "Friday":
                return 6;
            case "Saturday":
                return 7;
            default:
                return 0;
        }
    }

    //************************** Json Handling *************************************************
    public void getJsonEpisodes(String tmpUrl) {

        JsonArrayRequest showReq = new JsonArrayRequest(tmpUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        /*Toast.makeText(MainActivity.this,
                                "response: " + response.toString(),
                                Toast.LENGTH_LONG).show();*/

                        if (response.length() == 0 || response == null) {
                            Toast.makeText(SelectedShowActivity.this,
                                    Constant.NOT_FOUND_EP_MSG,
                                    Toast.LENGTH_LONG).show();
                            //responseReq = false;
                        }
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);

                                Episode episode = new Episode();
                                episode.setTitle(obj.getString(Constant.NAME_COL));
                                episode.setSummary(obj.getString(Constant.SUMMARY_COL));
                                episode.setEpisodeNum(Integer.parseInt(obj.getString(Constant.NUM_COL)));
                                episode.setSeason(Integer.parseInt(obj.getString(Constant.SEASON_COL)));
                                //when image is NULL, the value is in STRING format
                                // otherwise, it's an object:
                                if (obj.getString(Constant.IMG_COL).equals(Constant.NULL))
                                    episode.setImgUrl("");
                                else
                                    episode.setImgUrl(obj.getJSONObject(Constant.IMG_COL).
                                            getString(Constant.ORIGINAL_COL));

                                episode.setEpisodeDetails("Season " + obj.getString(Constant.SEASON_COL) +
                                        ", Episode " + obj.getString(Constant.NUM_COL) +
                                        ", air time " + obj.getString(Constant.DATE_COL) +
                                        " " + obj.getString(Constant.TIME_COL));

                                // adding movie to movies array
                                episodes.add(episode);

                                /*Toast.makeText(MainActivity.this,
                                        show.toString(),
                                        Toast.LENGTH_LONG).show();*/

                                //responseReq = false;//dialog.dismiss();
                                //mListener.onDialogResponse(false,"");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // mListener.onDialogResponse(false,"");
                        // responseReq = false;//dialog.dismiss();
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  VolleyLog.d(TAG, "Error: " + error.getMessage());
                //responseReq = false;//dialog.dismiss();
                //mListener.onDialogResponse(false,error.getMessage());
                dialog.dismiss();

            }
        });
        // Adding request to request queue
        Volley.newRequestQueue(this).add(showReq);
    }




}
