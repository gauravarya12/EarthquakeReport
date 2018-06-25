package com.example.ryder.earthquakereport;

import android.app.LoaderManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public final class QueryUtils {

    private QueryUtils(){

    }

    public static ArrayList<earthquake_detail> extractEarthquakes(String jsonResponse){
        Log.v("QueryUtils","Earthquakes extracted.");

        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        ArrayList<earthquake_detail> earthquakes = new ArrayList<>();


        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject root = new JSONObject(jsonResponse);

            JSONArray features = root.getJSONArray("features");

            for(int i=0;i<features.length();i++){
                JSONObject currentEarthquake = features.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double mag = Double.parseDouble(properties.getString("mag"));
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String earthquakeUrl = properties.getString("url");
                earthquakes.add(new earthquake_detail(mag, place, time, earthquakeUrl));
            }



        }

        catch(JSONException e){

            Log.e("QueryUtils","Problem, parsing the earthquake JSON results",e);
        }

        return earthquakes;
    }

    public static String getJsonData(String url) throws IOException {
        URL dataUrl = getUrl(url);
        String jsonResponse="";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) dataUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse =  readFromStream(inputStream);
            }
        }
        catch (IOException e){
            Log.e("QueryUtils","Error making the http connection",e);
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    public static String readFromStream(InputStream inputStream){
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {

            String line = bufferedReader.readLine();
            while (line != null) {
                sb.append(line);
                line = bufferedReader.readLine();
            }

        }
        catch (IOException e){
            Log.e("QueryUtils","Problem reading the input Stream fetched from the internet.",e);
        }
        return sb.toString();
    }

    public static URL getUrl(String url){
        try{
            return new URL(url);
        }
        catch (MalformedURLException e){
            Log.e("QueryUtils","Invalid URL",e);
            return null;
        }
    }
}
