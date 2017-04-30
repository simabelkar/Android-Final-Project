package com.example.sima.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RatingBar;

public class myZone extends AppCompatActivity  {

    MyClickListenerFromListFragment mListener;
    int orientation;
    Fragment frgFavorite, frgSchedule,mFragmentContainer,mStackFragments ;
    FragmentManager fm;

    AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(this);
    SQLiteDatabase db;
    ListView favoriteList;
    ListView scheduleList;
    CursorAdapterFav cursorAdapterFavorite;
    CursorAdapterSchdl cursorAdapterSchedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zone);

        //save the current orientation:
        orientation = getResources().getConfiguration().orientation;

        fm = getSupportFragmentManager();
        mFragmentContainer = fm.findFragmentById(R.id.fragmentContainer);

        frgFavorite = new FavoriteFragment();
        frgSchedule = new ScheduleFragment();
    }
}
