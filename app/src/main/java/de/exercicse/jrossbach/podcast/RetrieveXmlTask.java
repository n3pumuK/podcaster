package de.exercicse.jrossbach.podcast;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jrossbach on 16.07.17.
 */

public class RetrieveXmlTask extends AsyncTask {

    private String searchString;
    private List<PodcastItemVieModel> podcastItems = new ArrayList<>();

    public RetrieveXmlTask(final String searchString){
        this.searchString = searchString;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        XMLParser parser = new XMLParser();
        try {
            String xmlString = parser.getXmlFromUrl(searchString);
            Document document = parser.getDomElement(xmlString);

            NodeList nodes = document.getElementsByTagName("item");
            NodeList images = document.getElementsByTagName("image");

            for (int i = 0; i < nodes.getLength(); i++) {

                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nodes.item(i);

                map.put("title", parser.getValue(e, "title"));
                map.put("link", parser.getValue(e, "link"));
                map.put("guid", parser.getValue(e, "guid"));
                map.put("pubDate", parser.getValue(e, "pubDate"));
                map.put("category", parser.getValue(e, "category"));
                map.put("enclosure", parser.getValue(e, "enclosure"));

                podcastItems.add(new PodcastItemVieModel(map.get("title"),
                        map.get("enclosure"),
                        parser.getValue((Element) images.item(0), "url"),
                        map.get("category"),
                        map.get("pubDate")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<PodcastItemVieModel> getPodcastItems(){
        return podcastItems;
    }
}
