package de.exercicse.jrossbach.podcast.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import de.exercicse.jrossbach.podcast.R;
import de.exercicse.jrossbach.podcast.network.LoadImageTask;
import de.exercicse.jrossbach.podcast.network.PlayAudioTask;
import de.exercicse.jrossbach.podcast.search.PodcastItemView;
import de.exercicse.jrossbach.podcast.search.PodcastItemViewModel;


public class AudioPlayerFragment extends Fragment implements PodcastItemView {

    TextView titleTextView;
    Button playButton;
    Button stopButton;
    SeekBar seekBar;
    ImageView imageView;
    ProgressBar progressBar;
    private MediaPlayer mediaPlayer;
    private PodcastItemViewModel podcastItemViewModel;


    public static AudioPlayerFragment newInstance(){
        return new AudioPlayerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.audio_player_fragment, container, false);
        progressBar = rootView.findViewById(R.id.audio_progress_bar);
        titleTextView = rootView.findViewById(R.id.title_text_view);
        playButton = rootView.findViewById(R.id.play_button);
        stopButton = rootView.findViewById(R.id.stop_button);
        seekBar = rootView.findViewById(R.id.seek_bar);
        imageView = rootView.findViewById(R.id.item_image_view);
        podcastItemViewModel = getArguments().getParcelable("podcastItemViewModel");
        titleTextView.setText(podcastItemViewModel.getTitle());

        String imageUrl = podcastItemViewModel.getImageUrl();
        if(imageUrl != null) {
            LoadImageTask imageTask = new LoadImageTask(imageUrl, imageView, this);
            imageTask.execute();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpPlayButtons();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }
    }

    private void setUpPlayButtons(){
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    playButton.setText("Pause Streaming");
                    new PlayAudioTask(mediaPlayer, AudioPlayerFragment.this).execute(podcastItemViewModel.getUrl());
                    } else if (!mediaPlayer.isPlaying()){
                            mediaPlayer.start();
                    } else {
                    playButton.setText("Launch Streaming");

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }

                }

            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()|| mediaPlayer.isLooping()){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    playButton.setText("Play");
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            playButton.setText("Play");
        }
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
}
