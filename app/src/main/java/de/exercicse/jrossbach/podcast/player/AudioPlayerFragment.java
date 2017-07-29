package de.exercicse.jrossbach.podcast.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import de.exercicse.jrossbach.podcast.R;


public class AudioPlayerFragment extends Fragment {

    Button playButton;
    Button stopButton;
    SeekBar seekBar;
    private MediaPlayer player;

    public final String PODCAST_URL = "http://pc.argudiss.de";


    public static AudioPlayerFragment newInstance(){
        return new AudioPlayerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.audio_player_fragment, container, false);
        playButton = rootView.findViewById(R.id.play_button);
        stopButton = rootView.findViewById(R.id.stop_button);
        seekBar = rootView.findViewById(R.id.seek_bar);

        return rootView;
    }
}
