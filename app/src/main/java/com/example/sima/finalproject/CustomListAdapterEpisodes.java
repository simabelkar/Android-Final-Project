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

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sima on 18/05/2016.
 */
public class CustomListAdapterEpisodes extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Episode> episodesItems;
    ImageLoader imageLoader ;



    public CustomListAdapterEpisodes(Activity activity, List<Episode> episodesItems) {
        this.activity = activity;
        this.episodesItems = episodesItems;
    }

    @Override
    public int getCount() {
        return episodesItems.size();
    }

    @Override
    public Object getItem(int location) {
        return episodesItems.get(location);
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
            convertView = inflater.inflate(R.layout.activity_row, null);

       // getting show data for the row
       Episode episode = episodesItems.get(position);
       TextView title = (TextView) convertView.findViewById(R.id.title);
       TextView summary = (TextView) convertView.findViewById(R.id.summary);
       ImageView imageView = (ImageView)convertView.findViewById(R.id.imgVw);
       TextView episodeDtl = (TextView) convertView.findViewById(R.id.episode);


       // image-  Picasso does Automatic memory and disk caching:
       if (episode.getImgUrl().equals("")) {
           Picasso.with(convertView.getContext()).
                   load(R.drawable.img_not_fnd).into(imageView);
       }
       else{
           Picasso.with(convertView.getContext()).
                 load(episode.getImgUrl()).into(imageView);
       }


        // title
       title.setText(episode.getTitle());

       // summary- fixing HTML tags display
       //  summary.setText(show.getSummary());
       String formattedText = episode.getSummary();
       Spanned result = Html.fromHtml(formattedText);
       summary.setText(result);

       //episode date+time
       episodeDtl.setText(episode.getEpisodeDetails());

       return convertView;
    }
}
