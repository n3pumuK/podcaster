package de.exercicse.jrossbach.podcast.network.model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class PodcastChannelResponse {

    @Element(name = "channel")
    public Channel channel;

}
