package de.exercicse.jrossbach.podcast.network;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.exercicse.jrossbach.podcast.search.PodcastItemVieModel;
import de.exercicse.jrossbach.podcast.search.PodcastItemsView;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;


@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class RetrieveXmlTask extends AsyncTask {

    private String xml;
    private BufferedReader reader = null;
    private String searchString;
    private List<PodcastItemVieModel> podcastItems = new ArrayList<>();
    private XMLParser parser;
    private HttpURLConnection connection;
    private PodcastItemsView view;


    public RetrieveXmlTask(final String searchString, final PodcastItemsView view) {
        this.searchString = searchString;
        parser = new XMLParser();
        this.view = view;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            connection = (HttpURLConnection) (new URL(searchString)).openConnection();
            connection.setDoInput(true);
            connection.connect();


            if (connection.getResponseCode() < HTTP_BAD_REQUEST) {

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                inputStream.close();
                xml = builder.toString();

                Document document = parser.getDomElement(xml);
                NodeList nodes = document.getElementsByTagName("item");
                NodeList images = document.getElementsByTagName("image");

                for (int i = 0; i < nodes.getLength(); i++) {

                    HashMap<String, String> map = new HashMap<>();
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

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return xml;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(podcastItems.size() > 0){
            view.onItemsLoadedSuccessfully(podcastItems);
        }
    }
}
