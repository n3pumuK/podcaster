package de.exercicse.jrossbach.podcast.network.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Channel {

    @ElementList(name = "item", entry = "item", inline = true, required = false)
    public List<PodcastItem> itemList;

    @Element(name = "itunes:image", required = false)
    public ItunesImage channelImage;

    @Element(name = "image", required = false)
    public NormalImage normalImage;

}
