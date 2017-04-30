package com.example.sima.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sima on 20/03/2016.
 */
public final class AssingmentsDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "FinalProj.DB";

    private static final String SQL_CREATE_ENTRIES_FAV =
            "CREATE TABLE " + Constant.Shows.TABLE_FAVORITE + "(" +
                    Constant.Shows._ID + " INTEGER PRIMARY KEY," +
                    Constant.Shows.SHOW_ID_FAV + " TEXT UNIQUE," +
                    Constant.Shows.SHOW_NAME_FAV + " TEXT NOT NULL" + ");";

    private static final String SQL_CREATE_ENTRIES_SCHDL =
            "CREATE TABLE " + Constant.Shows.TABLE_SCHEDULE + "(" +
                    Constant.Shows._ID + " INTEGER PRIMARY KEY," +
                    Constant.Shows.SHOW_ID_SCHDL + " TEXT UNIQUE,"+
                    Constant.Shows.SHOW_NAME_SCHDL +" TEXT NOT NULL," +
                    Constant.Shows.SHOW_TIME_SCHDL +" TEXT NOT NULL" +  ");";

    private static final String SQL_DROP_TABLE_FAVORITE = "DROP TABLE IF EXISTS " + Constant.Shows.TABLE_FAVORITE;
    private static final String SQL_DROP_TABLE_SCHEDULE = "DROP TABLE IF EXISTS "+ Constant.Shows.TABLE_SCHEDULE;

    private static final String SQL_DELETE_FAV = "DELETE FROM " + Constant.Shows.TABLE_FAVORITE + " WHERE " +
            Constant.Shows.SHOW_ID_FAV + "=";
    private static final String SQL_DELETE_SCHDL = "DELETE FROM " + Constant.Shows.TABLE_SCHEDULE + " WHERE " +
            Constant.Shows.SHOW_ID_SCHDL + "=";

    public AssingmentsDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public static final void DeleteFavorite(Context context, String show_id)
    {
        AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Constant.Shows.TABLE_FAVORITE,Constant.Shows.SHOW_ID_FAV+"=?",new String[]{show_id});
        db.close();
    }

    public static final void DeleteSchedule(Context context, String show_id)
    {
        AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Constant.Shows.TABLE_SCHEDULE,Constant.Shows.SHOW_ID_SCHDL+"=?",new String[]{show_id});
        db.close();
    }

    public static final void InsertFavorite(Context context, String show_id, String show_name)
    {
        //save the show id in the DB
        long id;
        AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.Shows.SHOW_ID_FAV,show_id);
        values.put(Constant.Shows.SHOW_NAME_FAV,show_name);
        id = db.insert(Constant.Shows.TABLE_FAVORITE,null,values);
        db.close();
    }

    public static final void InsertSchedule(Context context, String show_id, String show_name, String show_time)
    {
        //save the show id in the DB
        long id;
        AssingmentsDBHelper dbHelper = new AssingmentsDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.Shows.SHOW_ID_SCHDL,show_id);
        values.put(Constant.Shows.SHOW_NAME_SCHDL,show_name);
        values.put(Constant.Shows.SHOW_TIME_SCHDL,show_time);
        id = db.insert(Constant.Shows.TABLE_SCHEDULE,null,values);
        db.close();
    }


    public  boolean isFavoriteShowExist(String showID){
       SQLiteDatabase db = getReadableDatabase();
        String[] selectArgs = {showID};
        Cursor cursor=db.rawQuery("SELECT " + Constant.Shows.SHOW_ID_FAV +
                " FROM " + Constant.Shows.TABLE_FAVORITE +
                " WHERE " + Constant.Shows.SHOW_ID_FAV  + "=?", selectArgs);

        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public  boolean isScheduleShowExist(String showID){
        SQLiteDatabase db = getReadableDatabase();
        String[] selectArgs = {showID};
        Cursor cursor=db.rawQuery("SELECT " + Constant.Shows.SHOW_ID_SCHDL +
                " FROM " + Constant.Shows.TABLE_SCHEDULE +
                " WHERE " + Constant.Shows.SHOW_ID_SCHDL  + "=?", selectArgs);

        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_FAV);
        db.execSQL(SQL_CREATE_ENTRIES_SCHDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_FAVORITE);
        db.execSQL(SQL_DROP_TABLE_SCHEDULE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}

