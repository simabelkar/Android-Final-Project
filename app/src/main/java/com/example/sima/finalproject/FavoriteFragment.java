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
import android.widget.RatingBar;
import android.widget.Toast;


public class FavoriteFragment extends Fragment {

    ListView favoriteList;

    AssingmentsDBHelper dbHelper;// = new AssingmentsDBHelper(this);
    SQLiteDatabase db;

    CursorAdapterFav cursorAdapterFavorite;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);


        favoriteList = (ListView)view.findViewById(R.id.listViewFav);

        AssingmentsDBHelper dbHelper= new AssingmentsDBHelper(getContext());

        //listViewFavorite adapter - populate the favorite list
        db = dbHelper.getReadableDatabase();
        Cursor cFavorite = db.query(
                Constant.Shows.TABLE_FAVORITE,
                null,
                null,
                null,
                null,
                null,
                null);
        cursorAdapterFavorite = new CursorAdapterFav(view.getContext(),cFavorite);
        favoriteList.setAdapter(cursorAdapterFavorite);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
