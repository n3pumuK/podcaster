package de.exercicse.jrossbach.podcast.network.model;


import org.simpleframework.xml.Attribute;

public class Enclosure {

    @Attribute(name = "url")
    private String url;

    @Attribute(name = "length")
    private String length;

    @Attribute(name = "type")
    private String type;

    public String getUrl() {
        return url;
    }

    public String getLength() {
        return length;
    }

    public String getType() {
        return type;
    }
}
