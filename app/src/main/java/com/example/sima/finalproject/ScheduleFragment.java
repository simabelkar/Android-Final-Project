package com.example.sima.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ScheduleFragment extends Fragment {
    MyClickListenerFromListFragment mListener;
    ListView scheduleList;
    SQLiteDatabase db;
    CursorAdapterSchdl cursorAdapterSchedule;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule,container,false);

       // scheduleList = (ListView)  view.findViewById(R.id.listViewSchdl);
        scheduleList = (ListView)view.findViewById(R.id.listViewSchdl);
        AssingmentsDBHelper dbHelper= new AssingmentsDBHelper(getContext());
        //listViewSchedule adapter - populate the schedule list
        db = dbHelper.getReadableDatabase();
        Cursor cSchedule = db.query(
                Constant.Shows.TABLE_SCHEDULE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterSchedule = new CursorAdapterSchdl(view.getContext(),cSchedule);
        scheduleList.setAdapter(cursorAdapterSchedule);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
