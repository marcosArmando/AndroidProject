package com.yucatancorp.androidproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImagenes extends AsyncTask<String, Void, Bitmap> {

    Bitmap imagen;

    @Override
    protected Bitmap doInBackground(String... strings) {

        String urlString = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+strings[0]+".png";

        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

            imagen = BitmapFactory.decodeStream(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imagen;
    }

}
