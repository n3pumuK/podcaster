package de.exercicse.jrossbach.podcast.search;

import java.util.Map;


public class PodcastItemVieModel {

    private String title;
    private String url;
    private String imageUrl;
    private String category;
    private String publishingDate;
    private String type;
    private String length;
    private Map<String, String> imageData;

    public PodcastItemVieModel(final String title, final String url, final String type, final String length,
                               final Map<String, String> imageData, final String category,
                               final String publishingDate){
        setTitle(title);
        setUrl(url);
        setImageData(imageData);
        setCategory(category);
        setPublishingDate(publishingDate);
        setType(type);
        setLength(length);
    }

    public PodcastItemVieModel(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Map<String, String> getImageData() {
        return imageData;
    }

    public void setImageData(Map<String, String> imageData) {
        this.imageData = imageData;
    }

}
