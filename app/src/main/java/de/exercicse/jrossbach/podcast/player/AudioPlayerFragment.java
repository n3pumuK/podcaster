package de.exercicse.jrossbach.podcast.player;

import android.app.NotificationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.exercicse.jrossbach.podcast.R;
import de.exercicse.jrossbach.podcast.search.PodcastItemView;
import de.exercicse.jrossbach.podcast.search.PodcastItemViewModel;


public class AudioPlayerFragment extends Fragment implements PodcastItemView, ExoPlayer.EventListener {

    @BindView(R.id.exo_progress)
    TimeBar seekBar;
    @BindView(R.id.exo_artwork)
    ImageView imageView;
    @BindView(R.id.audio_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.player_view)
    PlayerView playerView;

    private SimpleExoPlayer exoPlayer;
    private static MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private NotificationManager notificationManager;

    private PodcastItemViewModel podcastItemViewModel;
    private boolean playWhenReady;
    private int currentWindow;
    private long playbackPosition;


    public static AudioPlayerFragment newInstance() {
        return new AudioPlayerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.audio_player_fragment, container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            podcastItemViewModel = getArguments().getParcelable("podcastItemViewModel");
        }
        return rootView;
    }

    private void initPlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            exoPlayer = ExoPlayerFactory.newSimpleInstance(requireActivity());
            playerView.setPlayer(exoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    requireActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }


    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            playWhenReady = exoPlayer.getPlayWhenReady();
            exoPlayer.release();
            exoPlayer = null;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        initPlayer(Uri.parse(podcastItemViewModel.getUrl()));
    }

    @Override
    public void onStop() {
        releasePlayer();
        super.onStop();
    }

    @Override
    public void onItemsLoadedSuccessfully(List<PodcastItemViewModel> podcastItemViewModels) {

    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(PodcastItemViewModel podcastItemViewModel) {

    }


    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void play() {

    }
}
