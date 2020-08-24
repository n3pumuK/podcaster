package de.exercicse.jrossbach.podcaster.feature.player.domain;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import de.exercicse.jrossbach.podcaster.core.ui.NotificationHelper;
import de.exercicse.jrossbach.podcaster.R;


public class AudioPlaybackService extends IntentService implements AudioManager.OnAudioFocusChangeListener, ExoPlayer.EventListener {

    private static final String MEDIA_ROOT_ID = "media_root_id";
    private static final String EMPTY_MEDIA_ROOT_ID = "empty_root_id";
    private static final String ACTION_PLAY = "com.example.action.PLAY";
    private static final String LOG_TAG = "AudioPlayBackCervice";
    private static final String INTENT_EXTRA_MEDIA_URL = "intent_extra_media_url";
    private static final int NOTIFICATION_ID = 1;


    private MediaSessionCompat mediaSessionCompat;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private PlaybackStateCompat.Builder stateBuilder;
    private NotificationHelper notificationHelper;
    private SimpleExoPlayer exoPlayer;

    private Uri mediaUri;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AudioPlaybackService(String name) {
        super(name);
    }


    @Override
    public IBinder onBind(Intent intent) {

        if (intent != null) {
            mediaUri = intent.getData();
        }
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mediaUri = intent.getData();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationHelper = new NotificationHelper(getBaseContext());
        // Create a MediaSessionCompat
        mediaSessionCompat = new MediaSessionCompat(getApplicationContext(), LOG_TAG);

        // Enable callbacks from MediaButtons and TransportControls
        mediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSessionCompat.setPlaybackState(stateBuilder.build());

        // MySessionCallback() has methods that handle callbacks from a media controller
        mediaSessionCompat.setCallback(new AudioSessionCallBack(this));

        // Set the session's token so that client activities can communicate with it.

    }

    private boolean allowBrowsing(String clientPackageName, int clientUid) {
        return false;
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) mediaPlayer.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction().equals(ACTION_PLAY)) {
            mediaUri = intent.getData();
            initExoPlayer();
            startForeground(NOTIFICATION_ID, notificationHelper.buildForegroundNotification(mediaSessionCompat, getBaseContext()));
        }
        super.onStartCommand(intent, flags, startId);

        startForeground(NOTIFICATION_ID, notificationHelper.buildForegroundNotification(mediaSessionCompat, getBaseContext()));
        return Service.START_STICKY;
    }

    private void initExoPlayer() {
        // Create an instance of the ExoPlayer.
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getApplicationContext());

        // Set the ExoPlayer.EventListener to this activity.
        exoPlayer.addListener(this);

        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getApplicationContext(), getString(R.string.app_name));
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getBaseContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onAudioFocusChange(int focusState) {
        //Invoked when the audio focus of the system is updated.
        switch (focusState) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (exoPlayer == null) initExoPlayer();
                else if (!exoPlayer.isPlaying()) exoPlayer.setPlayWhenReady(true);
                exoPlayer.setVolume(1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
                exoPlayer.release();
                exoPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (exoPlayer.isPlaying()) exoPlayer.stop();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (exoPlayer.isPlaying()) exoPlayer.setVolume(0.1f);
                break;
        }
    }

    private boolean requestAudioFocus() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true;
        }
        //Could not gain focus
        return false;
    }

    private boolean removeAudioFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                audioManager.abandonAudioFocus(this);
    }

}
