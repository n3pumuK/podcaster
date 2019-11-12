package de.exercicse.jrossbach.podcast.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.exercicse.jrossbach.podcast.MainActivity;
import de.exercicse.jrossbach.podcast.R;
import de.exercicse.jrossbach.podcast.player.AudioPlayerFragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class PodcastListFragment extends Fragment implements PodcastChannelView {

    @BindView(R.id.podcast_recycler_view)
    RecyclerView recyclerView;
    PodcastItemAdapter adapter;
    private static String PODCAST_URL;

    @BindView(R.id.audio_progress_bar)
    ProgressBar progressBar;
    private SearchListPresenter presenter;

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
        presenter = new SearchListPresenter();
        View rootView = inflater.inflate(R.layout.podcast_list_fragment, container, false);
        ButterKnife.bind(this, rootView);
        PODCAST_URL = getArguments().getString("podcastUrl");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            PODCAST_URL = getArguments().getString("podcastUrl");
        }
        initRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        presenter.search(PODCAST_URL);
    }

    private void initRecyclerView() {
        adapter = new PodcastItemAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        presenter.detachView();
        super.onStop();
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
        ((MainActivity)requireActivity()).replaceCurrentFragment(fragment);
    }

}
