package de.exercicse.jrossbach.podcast.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;

import de.exercicse.jrossbach.podcast.Presenter;
import de.exercicse.jrossbach.podcast.search.PodcastItemView;


public class AudioPlayerPresenter extends Presenter<PodcastItemView> {

    private MediaPlayer mediaPlayer;
    private Context context;

    AudioPlayerPresenter(Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void playTrack() {
        context.startForegroundService(new Intent());
    }

    public void pauseTrack() {

    }

    public void stopTrack() {

    }
}
