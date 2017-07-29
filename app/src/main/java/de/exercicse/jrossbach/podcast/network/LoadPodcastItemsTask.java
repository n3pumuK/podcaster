package de.exercicse.jrossbach.podcast.network;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.exercicse.jrossbach.podcast.search.PodcastItemVieModel;
import de.exercicse.jrossbach.podcast.search.PodcastItemsView;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;


public class LoadPodcastItemsTask extends AsyncTask {

    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser parser;
    private BufferedReader reader = null;
    private String searchString;
    private List<PodcastItemVieModel> podcastItemList = new ArrayList<>();
    private HttpURLConnection connection;
    private PodcastItemsView view;
    private PodcastItemVieModel podcastItem;
    private InputStream inputStream;

    static final String PUB_DATE = "pubDate";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String TITLE = "title";
    static final String ITEM = "item";
    static final String CATEGORY = "category";
    static final String LINK = "link";
    static final String ENCLOSURE = "enclosure";
    static final String URL = "url";
    static final String TYPE = "type";
    static final String LENGTH = "length";



    public LoadPodcastItemsTask(final String searchString, final PodcastItemsView view){
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
            parser = xmlFactoryObject.newPullParser();
            inputStream = connection.getInputStream();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            boolean done = false;
            podcastItem = null;
            Map<String, String> imageDetails = null;

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
                                imageDetails.put("imageUrl",parser.nextText().trim());
                            }
                        }


                        if (name.equalsIgnoreCase(ITEM)) {
                            Log.i("new item", "Create new item");
                            podcastItem = new PodcastItemVieModel();
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
                                podcastItem.setUrl(parser.getAttributeValue(0));
                                podcastItem.setType(parser.getAttributeValue(1));
                                podcastItem.setLength(parser.getAttributeValue(2));
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
