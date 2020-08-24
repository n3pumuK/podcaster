package de.exercicse.jrossbach.podcaster.feature.player.domain;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;

import de.exercicse.jrossbach.podcaster.feature.search.ui.PodcastItemView;


@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class PlayAudioTask extends AsyncTask<String, Void, Boolean> {

    private MediaPlayer mediaPlayer;
    private PodcastItemView podcastItemView;


    public PlayAudioTask(final MediaPlayer mediaPlayer, final PodcastItemView podcastItemView){
        this.mediaPlayer = mediaPlayer;
        this.podcastItemView = podcastItemView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        podcastItemView.showProgress(true);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Boolean prepared;

        try {
            mediaPlayer.setDataSource(strings[0]);
            mediaPlayer.prepare();
            prepared = true;

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            prepared = false;
        } catch (SecurityException e) {
            e.printStackTrace();
            prepared = false;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            prepared = false;
        } catch (IOException e) {
            e.printStackTrace();
            prepared = false;
        }

        return prepared;
    }

    @Override
    protected void onPostExecute(Boolean prepared) {
        super.onPostExecute(prepared);
        podcastItemView.showProgress(false);
        podcastItemView.play();
    }
}
