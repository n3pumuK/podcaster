package de.exercicse.jrossbach.podcast.search;


import android.support.annotation.NonNull;

import java.util.List;

import de.exercicse.jrossbach.podcast.Presenter;
import io.reactivex.observers.DisposableSingleObserver;

public class SearchListPresenter extends Presenter<PodcastChannelView> {

    private PodcastChannelDataSource channelDataSource;

    SearchListPresenter() {
        channelDataSource = new PodcastChannelDataSource();
    }

    public void loadChannel(@NonNull String url) {
        view.showProgress(true);
        subscribe(channelDataSource.loadChannelItems(url), new DisposableSingleObserver<List<PodcastItemViewModel>>() {
            @Override
            public void onSuccess(List<PodcastItemViewModel> podcastItemViewModelList) {
                view.showProgress(false);
                view.onItemsLoadedSuccessfully(podcastItemViewModelList);
            }

            @Override
            public void onError(Throwable e) {
                view.showProgress(false);
                e.printStackTrace();
            }
        });
    }

}
