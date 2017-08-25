package de.exercicse.jrossbach.podcast.network.model;


import org.simpleframework.xml.Element;

public class NormalImage {

    @Element(name = "url")
    private String normalImageUrl;

    public String getNormalImageUrl() {
        return normalImageUrl;
    }
}
