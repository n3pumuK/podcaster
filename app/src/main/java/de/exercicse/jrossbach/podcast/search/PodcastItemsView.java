package de.exercicse.jrossbach.podcast.search;


import java.util.List;

public interface PodcastItemsView {

    void onItemsLoadedSuccessfully(List<PodcastItemVieModel> podcastItemVieModels);
    void showProgress(boolean show);

}
