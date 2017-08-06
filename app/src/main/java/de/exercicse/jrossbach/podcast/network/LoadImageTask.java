package de.exercicse.jrossbach.podcast.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.exercicse.jrossbach.podcast.search.PodcastItemView;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;


@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class LoadImageTask extends AsyncTask {

    private String imageUrl;
    private PodcastItemView view;
    private ImageView imageView;

    public LoadImageTask(String imageUrl, ImageView imageView, PodcastItemView view){
        this.imageUrl = imageUrl;
        this.imageView = imageView;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        view.showProgress(true);
    }

    @Override
    protected Bitmap doInBackground(Object[] objects) {
        HttpURLConnection connection = null;
        InputStream inputStream;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            int resultCode = connection.getResponseCode();

            if(resultCode < HTTP_BAD_REQUEST){
                inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection!= null){
                connection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!= null){
            imageView.setImageBitmap((Bitmap) bitmap);
        }
        view.showProgress(false);
    }
}
