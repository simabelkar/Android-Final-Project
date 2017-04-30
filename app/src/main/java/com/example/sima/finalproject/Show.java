package com.example.sima.finalproject;

/**
 * Created by sima on 18/05/2016.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * This class refer to both Show & Episode item
 *
 */
public class Show extends Episode {


    private String scheduleTime;
    private ArrayList<String> scheduleDays;
    private Boolean isFavoritePressed;
    private Boolean isSchedulePressed;
    private String yearPremiered;
    private String network;
    private ArrayList<String> genres;



    // C-tors: ***********************************************************************
    public Show() {
    }

    public Show(String name, String imageUrl, String summary) {
        this.title = name;
        this.imageUrl = imageUrl;
        this.summary = summary;
    }

    public Show(String name, String imageUrl, String summary,String episodeDetails,String idShow ) {
        this.title = name;
        this.imageUrl = imageUrl;
        this.summary = summary;
        this.episodeDetails = episodeDetails;
        this.idShow=idShow;
    }

    // Getters & Setters: ******************************************************************

    public Boolean getFavoritePressed() {
        return isFavoritePressed;
    }
    public void setFavoritePressed(Boolean favoritePressed) {
        isFavoritePressed = favoritePressed;
    }

    public Boolean getSchedulePressed() {
        return isSchedulePressed;
    }

    public void setSchedulePressed(Boolean schedulePressed) {
        isSchedulePressed = schedulePressed;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public List<String> getScheduleDays() {
        return scheduleDays;
    }
    public void setScheduleDays(ArrayList<String> scheduleDays) {
        this.scheduleDays = scheduleDays;
    }
    public String getScheduleDaysByIndex(int index){return scheduleDays.get(index);}

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getYearPremiered() {
        return yearPremiered;
    }

    public void setYearPremiered(String yearPremiered) {
        this.yearPremiered = yearPremiered;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
    public String getGenresByIndex(int index){return genres.get(index);}

    // *************************************************************************************
    @Override
       public String toString(){
        return "Title: "+ this.title+ ", Summary: " + this.summary;
        }
}
