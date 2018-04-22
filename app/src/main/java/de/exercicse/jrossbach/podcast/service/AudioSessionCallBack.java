package de.exercicse.jrossbach.podcast.service;

import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;



public class AudioSessionCallBack extends MediaSessionCompat.Callback {

    Context context;
    public AudioSessionCallBack(Context context) {
     this.context = context;
    }

    @Override
    public void onPlay() {
        super.onPlay();
    }

    @Override
    public void onPrepare() {
        super.onPrepare();
    }

    @Override
    public void onFastForward() {
        super.onFastForward();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
