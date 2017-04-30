package com.example.sima.finalproject;

import java.util.List;

/**
 * Created by sima on 07/07/2016.
 */
public class Episode {

    protected String idShow;
    protected String title, imageUrl;
    protected String summary;
    protected String episodeDetails;
    protected Integer season;
    protected Integer episodeNum;

    // C-tors: ***********************************************************************
    public Episode() {
    }

    public Episode(String name, String imageUrl, String summary) {
        this.title = name;
        this.imageUrl = imageUrl;
        this.summary = summary;
    }

    //episode
    public Episode(String name, String imageUrl, String summary,String episodeDetails,String idShow ) {
        this.title = name;
        this.imageUrl = imageUrl;
        this.summary = summary;
        this.episodeDetails = episodeDetails;
        this.idShow=idShow;
    }

    // Getters & Setters: ******************************************************************
    public void setIdShow(String idShow) {
        this.idShow = idShow;
    }
    public String getIdShow() {
        return idShow;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    }

    public String getImgUrl() {
        return imageUrl;
    }
    public void setImgUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setEpisodeDetails(String episodeDetails) {
        this.episodeDetails = episodeDetails;
    }
    public String getEpisodeDetails() {
        return episodeDetails;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }
    public Integer getSeason() {
        return season;
    }

    public Integer getEpisodeNum() {
        return episodeNum;
    }
    public void setEpisodeNum(Integer episodeNum) {
        this.episodeNum = episodeNum;
    }

    // *************************************************************************************
    @Override
    public String toString(){
        return "Title: "+ this.title+ ", Summary: " + this.summary;
    }

}
