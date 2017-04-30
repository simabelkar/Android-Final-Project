package com.example.sima.finalproject;


import android.content.Context;
import android.view.View;

public interface MyClickListenerFromListFragment {
    public void onButtonClickFind(View view);
    public void onButtonClickBack(View view);
    public void onItemClick(View view,int position);
    public void onFavoriteClick(Context context,String show_id, String show_name, boolean isFavorite);
    public void onScheduleClick(Context context, String show_id, String show_name, String show_time, boolean isScheduled);
    public void onMyZoneClick(View view);

    public void onDialogResponse(boolean activateDialog, String err);
    public void makeToast(String msg);

}
