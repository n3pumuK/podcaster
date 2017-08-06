package de.exercicse.jrossbach.podcast.search;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;


public class PodcastItemViewModel implements Parcelable {

    private String title;
    private String url;
    private String imageUrl;
    private String category;
    private String publishingDate;
    private String type;
    private String length;
    private Map<String, String> imageData;

    public PodcastItemViewModel(final String title, final String url, final String type, final String length,
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

    public PodcastItemViewModel(){

    }

    protected PodcastItemViewModel(Parcel in) {
        title = in.readString();
        url = in.readString();
        imageUrl = in.readString();
        category = in.readString();
        publishingDate = in.readString();
        type = in.readString();
        length = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(imageUrl);
        dest.writeString(category);
        dest.writeString(publishingDate);
        dest.writeString(type);
        dest.writeString(length);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PodcastItemViewModel> CREATOR = new Creator<PodcastItemViewModel>() {
        @Override
        public PodcastItemViewModel createFromParcel(Parcel in) {
            return new PodcastItemViewModel(in);
        }

        @Override
        public PodcastItemViewModel[] newArray(int size) {
            return new PodcastItemViewModel[size];
        }
    };

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
