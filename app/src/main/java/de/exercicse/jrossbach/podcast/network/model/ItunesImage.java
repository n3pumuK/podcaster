package de.exercicse.jrossbach.podcast.network.model;

import org.simpleframework.xml.Element;

public class ItunesImage {

    @Element(name = "href")
    private String itunesImageUrl;

    public String getItunesImageUrl() {
        return itunesImageUrl;
    }
}
