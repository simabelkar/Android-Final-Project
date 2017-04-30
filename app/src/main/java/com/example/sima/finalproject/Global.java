package com.example.sima.finalproject;

import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by sima on 07/07/2016.
 */
public class Global {
    //Members
    private static JsonExtractor jsonExtractor;
    private static boolean isEpisodeList=false;
    private static String strQuery;
    private static String strChosenShow;
    private static String idChosenShow;
    private static String timeChosenShow;
    private static TextView tvTitleResults;

    private static TextView tvGnr,tvGnrVal,tvYr, tvYrVal, tvNw, tvNwVal,tvSch,tvSchVal;
    private static RatingBar rtb;
    private static Switch swtFav;

    //getters & Setters
    public static JsonExtractor getJsonExtractor() {
        return jsonExtractor;
    }
    public static void setJsonExtractor(JsonExtractor jsonExtractor) {
        Global.jsonExtractor = jsonExtractor;
    }

    public static boolean isEpisodeList() {
        return isEpisodeList;
    }
    public static void setEpisodeList(boolean episodeList) {
        isEpisodeList = episodeList;
    }

    public static String getStrQuery() {
        return strQuery;
    }
    public static void setStrQuery(String strQuery) {
        Global.strQuery = strQuery;
    }

    public static String getStrChosenShow() {
        return strChosenShow;
    }

    public static void setStrChosenShow(String strChosenShow) {
        Global.strChosenShow = strChosenShow;
    }
    public static String getIdChosenShow(){
        return idChosenShow;
    }
    public static void setIdChosenShow(String idChosenShow){
        Global.idChosenShow = idChosenShow;
    }

    public static TextView getTvTitleResults() {
        return tvTitleResults;
    }
    public static void setTvTitleResults(TextView tvTitleResults) {
        Global.tvTitleResults = tvTitleResults;
    }

    /*
    * parameter: View.GONE - hide
    * View.VISIBLE - show
    * */
    public static void SetVisibilityShowDetails(int visibility){

        tvGnr.setVisibility(visibility);
        tvGnrVal.setVisibility(visibility);
        tvYr.setVisibility(visibility);
        tvYrVal.setVisibility(visibility);
        tvNw.setVisibility(visibility);
        tvNwVal.setVisibility(visibility);
        tvSch.setVisibility(visibility);
        tvSchVal.setVisibility(visibility);
        rtb.setVisibility(visibility);
        swtFav.setVisibility(visibility);
    }

    public static RatingBar getRtb() {
        return rtb;
    }

    public static void setRtb(RatingBar rtb) {
        Global.rtb = rtb;
    }

    public static Switch getSwtFav() {
        return swtFav;
    }

    public static void setSwtFav(Switch swtFav) {
        swtFav.setChecked(false);
        Global.swtFav = swtFav;
    }

    public static TextView getTvGnr() {
        return tvGnr;
    }

    public static void setTvGnr(TextView tvGnr) {
        Global.tvGnr = tvGnr;
    }

    public static TextView getTvGnrVal() {
        return tvGnrVal;
    }

    public static void setTvGnrVal(TextView tvGnrVal) {
        Global.tvGnrVal = tvGnrVal;
    }

    public static TextView getTvNw() {
        return tvNw;
    }

    public static void setTvNw(TextView tvNw) {
        Global.tvNw = tvNw;
    }

    public static TextView getTvNwVal() {
        return tvNwVal;
    }

    public static void setTvNwVal(TextView tvNwVal) {
        Global.tvNwVal = tvNwVal;
    }

    public static TextView getTvSch() {
        return tvSch;
    }

    public static void setTvSch(TextView tvSch) {
        Global.tvSch = tvSch;
    }

    public static TextView getTvSchVal() {
        return tvSchVal;
    }

    public static void setTvSchVal(TextView tvSchVal) {
        Global.tvSchVal = tvSchVal;
    }

    public static TextView getTvYr() {
        return tvYr;
    }

    public static void setTvYr(TextView tvYr) {
        Global.tvYr = tvYr;
    }

    public static TextView getTvYrVal() {
        return tvYrVal;
    }

    public static void setTvYrVal(TextView tvYrVal) {
        Global.tvYrVal = tvYrVal;
    }

    public static String getTimeChosenShow()
    {
        return timeChosenShow;
    }

    public static void setTimeChosenShow(String time)
    {
        Global.timeChosenShow = time;
    }
}
