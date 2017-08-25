package de.exercicse.jrossbach.podcast.network.model;

import org.simpleframework.xml.Element;

public class PodcastItem {

    @Element(name = "title")
    private String title;

    @Element(name = "link", required = false)
    private String link;

    @Element(name = "description", required = false)
    private String description;

    @Element(name = "category")
    private String category;

    @Element(name = "enclosure")
    private Enclosure enclosure;

    @Element(name = "guid")
    private String guid;

    @Element(name = "pubDate")
    private String pubDate;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public String getGuid() {
        return guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getCategory() {
        return category;
    }
}