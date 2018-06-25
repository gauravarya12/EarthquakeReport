package com.example.ryder.earthquakereport;



import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<earthquake_detail>> {

    private String queryUrl;

    public EarthquakeLoader(Context context, String url){
        super(context);
        queryUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v("EarthquakeLoader","onStartLoading() Method started.");
        forceLoad();
    }

    @Override
    public ArrayList<earthquake_detail> loadInBackground() {

        Log.v("EarthquakeLoader","loadInBackground() started.");
        ArrayList<earthquake_detail> earthquakeDetailArrayList = null;
        try {
            String jsonData = QueryUtils.getJsonData(queryUrl);
            return QueryUtils.extractEarthquakes(jsonData);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return earthquakeDetailArrayList;
    }

}
