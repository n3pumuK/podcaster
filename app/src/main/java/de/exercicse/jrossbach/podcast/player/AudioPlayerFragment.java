package de.exercicse.jrossbach.podcast.player;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.exercicse.jrossbach.podcast.R;
import de.exercicse.jrossbach.podcast.network.PlayAudioTask;
import de.exercicse.jrossbach.podcast.search.PodcastItemView;
import de.exercicse.jrossbach.podcast.search.PodcastItemViewModel;
import de.exercicse.jrossbach.podcast.service.AudioPlaybackService;


public class AudioPlayerFragment extends Fragment implements PodcastItemView {

    @BindView(R.id.title_text_view)
    TextView titleTextView;
    @BindView(R.id.duration)
    TextView duration;
    @BindView(R.id.play_button)
    FloatingActionButton playButton;
    @BindView(R.id.stop_button)
    FloatingActionButton stopButton;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.item_image_view)
    ImageView imageView;
    @BindView(R.id.audio_progress_bar)
    ProgressBar progressBar;
    private MediaPlayer mediaPlayer;
    private PodcastItemViewModel podcastItemViewModel;
    private boolean initialStage = true;
    private boolean playPause;
    private double finalTime = 0;
    private double timeElapsed = 0;
    private Handler durationHandler = new Handler();



    public static AudioPlayerFragment newInstance() {
        return new AudioPlayerFragment();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.audio_player_fragment, container, false);
        ButterKnife.bind(this, rootView);
        podcastItemViewModel = getArguments().getParcelable("podcastItemViewModel");
        titleTextView.setText(podcastItemViewModel.getTitle());
        String imageUrl = podcastItemViewModel.getImageUrl();
        if (imageUrl != null) {
            Glide.with(this)
                    .load(imageUrl)
                    .into(imageView);
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setScreenOnWhilePlaying(false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpPlayButtons();
        setUpSeekBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
    }

    private void setUpPlayButtons() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                if (!playPause) {
                    playButton.setImageResource(R.drawable.ic_pause_24dp);
                    if (initialStage) {
                        Intent intent = new Intent(getActivity(), AudioPlaybackService.class);
                        intent.setAction("com.example.action.PLAY");
                        intent.putExtra("intent_extra_media_url", podcastItemViewModel.getUrl());
                        intent.setData(Uri.parse(podcastItemViewModel.getUrl()));
                        getActivity().startService(intent);
//                        new PlayAudioTask(mediaPlayer, AudioPlayerFragment.this)
//                                .execute(podcastItemViewModel.getUrl());
                    } else {
                        if (!mediaPlayer.isPlaying())
                            play();
                    }
                    playPause = true;

                } else {
                    playButton.setImageResource(R.drawable.ic_play_arrow_24dp);
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    playPause = false;
                }

            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                playButton.setImageResource(R.drawable.ic_play_arrow_24dp);
                playPause = false;
            }
        });
    }

    public void play() {
        finalTime = mediaPlayer.getDuration();
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        timeElapsed = mediaPlayer.getCurrentPosition();
        seekBar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 1000);
    }

    //handler to change seekBarTime
    private Runnable updateSeekBarTime = new Runnable() {

        public void run() {
            if (mediaPlayer != null) {
                timeElapsed = mediaPlayer.getCurrentPosition();
                seekBar.setProgress((int) timeElapsed);
                //set time remaining
                double timeRemaining = finalTime - timeElapsed;
                duration.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining),
                        TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining)
                                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

                //repeat yourself that again in 1 second
                durationHandler.postDelayed(this, 1000);
            }
        }
    };


    private void setUpSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
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
