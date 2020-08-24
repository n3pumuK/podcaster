package de.exercicse.jrossbach.podcaster.feature.search.ui;


import java.util.List;

public interface PodcastChannelView {

    void onItemsLoadedSuccessfully(List<PodcastItemViewModel> podcastItemViewModels);
    void showProgress(boolean show);
    void onItemClick(PodcastItemViewModel podcastItemViewModel);
    void showError(String message);
}
