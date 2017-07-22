package de.exercicse.jrossbach.podcast.search;

import android.widget.ImageView;

/**
 * Created by jrossbach on 15.07.17.
 */

public class PodcastItemVieModel {

    String title;
    String url;
    String imageUrl;
    String category;
    String publishingDate;

    public PodcastItemVieModel(final String title, final String url,
                               final String imageUrl, final String category,
                               final String publishingDate){
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.category = category;
        this.publishingDate = publishingDate;
    }
}
