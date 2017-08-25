package de.exercicse.jrossbach.podcast.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import de.exercicse.jrossbach.podcast.MainActivity;
import de.exercicse.jrossbach.podcast.R;
import de.exercicse.jrossbach.podcast.network.ApiProvider;
import de.exercicse.jrossbach.podcast.network.model.PodcastChannelResponse;
import de.exercicse.jrossbach.podcast.network.model.PodcastItem;
import de.exercicse.jrossbach.podcast.player.AudioPlayerFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class PodcastListFragment extends Fragment implements PodcastChannelView {

    RecyclerView recyclerView;
    PodcastItemAdapter adapter;
    private static String PODCAST_URL;
    ProgressBar progressBar;
    Disposable disposable;

    public static PodcastListFragment newInstance(String url){
        PodcastListFragment fragment = new PodcastListFragment();
        Bundle args = new Bundle();
        args.putString("podcastUrl", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.podcast_list_fragment, container, false);
        PODCAST_URL = getArguments().getString("podcastUrl");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.podcast_recycler_view);
        progressBar = view.findViewById(R.id.audio_progress_bar);
        initRecyclerView();
        loadChannel();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        if(bundle != null){
            PODCAST_URL = getArguments().getString("podcastUrl");
        }
    }

    private void initRecyclerView() {
        adapter = new PodcastItemAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void loadChannel() {
        showProgress(true);
        disposable = ApiProvider.getChannelApi(PODCAST_URL)
                .getPodcastChannelResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PodcastChannelResponse>() {
                    @Override
                    public void onSuccess(PodcastChannelResponse response) {

                        showProgress(false);
                        List<PodcastItemViewModel> podcastItemViewModelList = new ArrayList<>();
                        List<PodcastItem> itemList = response.channel.itemList;
                        if(itemList != null){
                            for (PodcastItem item : itemList){
                                PodcastItemViewModel podcastItemViewModel = new PodcastItemViewModel();
                                podcastItemViewModel.setTitle(item.getTitle());
                                podcastItemViewModel.setCategory(item.getCategory());
                                podcastItemViewModel.setPublishingDate(item.getPubDate());
                                podcastItemViewModel.setUrl(item.getEnclosure().getUrl());
                                podcastItemViewModel.setType(item.getEnclosure().getType());
                                podcastItemViewModel.setLength(item.getEnclosure().getLength());
                                String imageUrl = response.channel.normalImage.getNormalImageUrl() != null ? response.channel.normalImage.getNormalImageUrl() : response.channel.channelImage.getItunesImageUrl();
                                podcastItemViewModel.setImageUrl(imageUrl);
                                podcastItemViewModelList.add(podcastItemViewModel);
                            }
                        }
                        onItemsLoadedSuccessfully(podcastItemViewModelList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgress(false);
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(!disposable.isDisposed()){
            disposable.dispose();
        }
    }

    @Override
    public void onItemsLoadedSuccessfully(List<PodcastItemViewModel> podcastItemViewModels) {
        adapter.setItems(podcastItemViewModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? VISIBLE : GONE);
    }

    @Override
    public void onItemClick(PodcastItemViewModel podcastItemViewModel) {
        AudioPlayerFragment fragment = AudioPlayerFragment.newInstance();
        Bundle args = new Bundle();
        args.putParcelable("podcastItemViewModel", podcastItemViewModel);
        fragment.setArguments(args);
        ((MainActivity)getActivity()).replaceCurrentFragment(fragment);
    }

}
