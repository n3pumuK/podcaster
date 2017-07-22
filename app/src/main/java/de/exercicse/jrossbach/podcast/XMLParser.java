package de.exercicse.jrossbach.podcast;

import android.support.annotation.NonNull;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by jrossbach on 16.07.17.
 */

public class XMLParser {

    public String getXmlFromUrl(@NonNull String url) throws IOException {
        String xml;
        BufferedReader reader = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            InputStream inputStream = conn.getInputStream();
            if(inputStream!=null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                final StringBuffer sb = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                xml = sb.toString();
                return xml;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }


    public Document getDomElement(String xml) {
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();

            InputSource is = new InputSource();
            if (xml != null) {
                is.setCharacterStream(new StringReader(xml));
                document = db.parse(is);
            }
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        }
        // return DOM
        return document;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue(Node element) {
        Node child;
        if (element != null) {
            if (element.hasChildNodes()) {
                for (child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
