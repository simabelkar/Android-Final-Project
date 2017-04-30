package com.example.sima.finalproject;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sima on 15/07/2016.
 */
public class CustomListAdapter2Day extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Show> showItems;
    /* private List<Episode> episodesItems;
    ImageLoader imageLoader ;*/
    private String scheduleTime;
    private ArrayList<String> scheduleDays;
    private String yearPremiered;
    private String network;
    private ArrayList<String> genres;


    public CustomListAdapter2Day(Activity activity, List<Show> showItems) {
        this.activity = activity;
        this.showItems = showItems;
    }

    /*public CustomListAdapter(Activity activity, List<Episode> episodesItems,boolean bl) {
        this.activity = activity;
        this.episodesItems = episodesItems;
    }*/

    @Override
    public int getCount() {
        return showItems.size();
    }

    @Override
    public Object getItem(int location) {
        return showItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_row_show, null);

        // getting show data for the row
        Show show = showItems.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView summary = (TextView) convertView.findViewById(R.id.summary);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imgVw);

        TextView year= (TextView) convertView.findViewById(R.id.tvYearVal);
        TextView genre= (TextView) convertView.findViewById(R.id.tvGenreVal);
        TextView network= (TextView) convertView.findViewById(R.id.tvNetVal);
        TextView schedule= (TextView) convertView.findViewById(R.id.tvScheduleVal);

        // image-  Picasso does Automatic memory and disk caching:
        if (show.getImgUrl().equals("")) {
            Picasso.with(convertView.getContext()).
                    load(R.drawable.img_not_fnd).into(imageView);
        }
        else{
            Picasso.with(convertView.getContext()).
                    load(show.getImgUrl()).into(imageView);
        }


        // title
        title.setText(show.getTitle());

        //main info:
        year.setText(show.getYearPremiered().toString().substring(0,4));
        network.setText(show.getNetwork() );
        String tmp="";
        for (int j = 0; j < show.getGenres().size(); j++)
            tmp+=show.getGenresByIndex(j)+" | ";
        genre.setText(tmp);
        tmp="";
        for (int j = 0; j < show.getScheduleDays().size(); j++)
            tmp+=show.getScheduleDaysByIndex(j)+" | ";
        schedule.setText(tmp+" "+ show.getScheduleTime());

        // summary- fixing HTML tags display
        //  summary.setText(show.getSummary());
        String formattedText;
        if(show.getSummary().length()>150)
            formattedText= show.getSummary().substring(0,150)+"...";
        else
            formattedText=show.getSummary();
        Spanned result = Html.fromHtml(formattedText);
        summary.setText(result);

        return convertView;
    }
}
