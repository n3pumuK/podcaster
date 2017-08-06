package de.exercicse.jrossbach.podcast.search;


import java.util.List;

public interface PodcastItemView {

    void onItemsLoadedSuccessfully(List<PodcastItemViewModel> podcastItemViewModels);
    void showProgress(boolean show);
    void onItemClick(PodcastItemViewModel podcastItemViewModel);

}
