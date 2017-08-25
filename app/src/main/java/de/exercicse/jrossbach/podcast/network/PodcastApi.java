package de.exercicse.jrossbach.podcast.network;


import de.exercicse.jrossbach.podcast.network.model.PodcastChannelResponse;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface PodcastApi {


    @GET("/")
    Single<PodcastChannelResponse>getPodcastChannelResponse();
}
