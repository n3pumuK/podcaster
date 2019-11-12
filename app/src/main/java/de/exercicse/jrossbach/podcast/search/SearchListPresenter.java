package de.exercicse.jrossbach.podcast.search;


import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.List;

import de.exercicse.jrossbach.podcast.Presenter;
import io.reactivex.observers.DisposableSingleObserver;

public class SearchListPresenter extends Presenter<PodcastChannelView> {

    private PodcastDataSource channelDataSource;

    SearchListPresenter() {
        channelDataSource = new PodcastDataSource();
    }

    public void search(@NonNull String searchString) {
        view.showProgress(true);
        subscribe(channelDataSource.loadChannelItems(Uri.encode(searchString)), new DisposableSingleObserver<List<PodcastItemViewModel>>() {
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
