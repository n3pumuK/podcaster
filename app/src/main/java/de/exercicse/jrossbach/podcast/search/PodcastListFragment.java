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

import java.util.List;

import de.exercicse.jrossbach.podcast.R;
import de.exercicse.jrossbach.podcast.network.LoadPodcastItemsTask;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class PodcastListFragment extends Fragment implements PodcastItemsView{

    RecyclerView recyclerView;
    PodcastItemAdapter adapter;
    List<PodcastItemVieModel> itemList;
    private static String PODCAST_URL;
    ProgressBar progressBar;

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
        progressBar = view.findViewById(R.id.progress_bar);
        initRecyclerView();
        loadItems();
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

    private void loadItems(){
        LoadPodcastItemsTask loadPodcastItemsTask = new LoadPodcastItemsTask(PODCAST_URL, this);
        loadPodcastItemsTask.execute();
    }

    @Override
    public void onItemsLoadedSuccessfully(List<PodcastItemVieModel> podcastItemVieModels) {
        adapter.setItems(podcastItemVieModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? VISIBLE : GONE);
    }
}
