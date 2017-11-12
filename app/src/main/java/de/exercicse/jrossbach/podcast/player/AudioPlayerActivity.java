package de.exercicse.jrossbach.podcast.player;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;

import de.exercicse.jrossbach.podcast.service.AudioPlaybackService;


public class AudioPlayerActivity extends AppCompatActivity {

    private MediaBrowserCompat mediaBrowser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create MediaBrowserServiceCompat
        mediaBrowser = new MediaBrowserCompat(this,
                new ComponentName(this, AudioPlaybackService.class),
                connectionCallbacks,
                null);
    }

    private final MediaBrowserCompat.ConnectionCallback connectionCallbacks =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {

                    // Get the token for the MediaSession
                    MediaSessionCompat.Token token = mediaBrowser.getSessionToken();

                    // Create a MediaControllerCompat
                    MediaControllerCompat mediaController =
                            null;
                    try {
                        mediaController = new MediaControllerCompat(AudioPlayerActivity.this, // Context
                                token);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    // Save the controller
                    MediaControllerCompat.setMediaController(AudioPlayerActivity.this, mediaController);

                    // Finish building the UI
                    //buildTransportControls();
                }

                @Override
                public void onConnectionSuspended() {
                    // The Service has crashed. Disable transport controls until it automatically reconnects
                    super.onConnectionSuspended();
                }

                @Override
                public void onConnectionFailed() {
                    // The Service has refused our connection
                    super.onConnectionFailed();
                }
            };
}
