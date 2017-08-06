package de.exercicse.jrossbach.podcast.network;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.exercicse.jrossbach.podcast.search.PodcastItemViewModel;
import de.exercicse.jrossbach.podcast.search.PodcastItemView;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;


public class LoadPodcastItemsTask extends AsyncTask {

    private XmlPullParserFactory xmlFactoryObject;
    private String searchString;
    private List<PodcastItemViewModel> podcastItemList = new ArrayList<>();
    private HttpURLConnection connection;
    private PodcastItemView view;

    private static final String PUB_DATE = "pubDate";
    private static final String CHANNEL = "channel";
    private static final String TITLE = "title";
    private static final String ITEM = "item";
    private static final String CATEGORY = "category";
    private static final String ENCLOSURE = "enclosure";
    private static final String URL = "url";
    private static final String TYPE = "type";
    private static final String LENGTH = "length";



    public LoadPodcastItemsTask(final String searchString, final PodcastItemView view){
        this.searchString = searchString;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        view.showProgress(true);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            connection = (HttpURLConnection) (new URL(searchString)).openConnection();
            connection.setDoInput(true);
            connection.connect();

            if(connection.getResponseCode() < HTTP_BAD_REQUEST)
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser parser = xmlFactoryObject.newPullParser();
            InputStream inputStream = connection.getInputStream();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            boolean done = false;
            PodcastItemViewModel podcastItem = null;
            Map<String, String> imageDetails = null;
            String imageUrl = null;

            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if(name.equalsIgnoreCase("image")) {
                            imageDetails = new HashMap<>();
                        } else if (imageDetails != null) {
                            if(name.equalsIgnoreCase("width")){
                                imageDetails.put("width", parser.nextText().trim());
                            } else if(name.equalsIgnoreCase("height")){
                                imageDetails.put("height", parser.nextText().trim());
                            } else if(name.equalsIgnoreCase("url")){
                                imageUrl = parser.nextText().trim();
                                imageDetails.put("imageUrl", imageUrl);

                            }
                        }

                        if(name.equalsIgnoreCase("itunes:image")){
                            imageUrl = parser.getAttributeValue(null, "href");
                        }

                        if (name.equalsIgnoreCase(ITEM)) {
                            Log.i("new item", "Create new item");
                            podcastItem = new PodcastItemViewModel();
                        } else if (podcastItem != null) {
                             if (name.equalsIgnoreCase(TITLE)) {
                                Log.i("Attribute", TITLE);
                                podcastItem.setTitle(parser.nextText().trim());
                            } else if (name.equalsIgnoreCase(PUB_DATE)) {
                                Log.i("Attribute", "date");
                                podcastItem.setPublishingDate(parser.nextText());
                            } else if (name.equalsIgnoreCase(CATEGORY)) {
                                Log.i("Attribute", CATEGORY);
                                podcastItem.setCategory(parser.nextText().trim());
                            } else if(name.equalsIgnoreCase(ENCLOSURE)){
                                podcastItem.setUrl(parser.getAttributeValue(null, URL));
                                podcastItem.setType(parser.getAttributeValue(null, TYPE));
                                podcastItem.setLength(parser.getAttributeValue(null, LENGTH));
                                 Log.i("Attribute", URL);
                                 Log.i("Attribute", TYPE);
                                 Log.i("Attribute", LENGTH);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase(ITEM) && podcastItem != null) {
                            Log.i("Added", podcastItem.toString());
                            podcastItem.setImageData(imageDetails);
                            podcastItem.setImageUrl(imageUrl);
                            podcastItemList.add(podcastItem);
                        } else if (name.equalsIgnoreCase(CHANNEL)) {
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
            inputStream.close();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null ){
                connection.disconnect();
            }
        }

        return podcastItemList;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(podcastItemList.size() > 0){
            view.onItemsLoadedSuccessfully(podcastItemList);
        } view.showProgress(false);
    }

}
