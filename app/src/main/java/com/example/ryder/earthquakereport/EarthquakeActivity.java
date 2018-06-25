package com.example.ryder.earthquakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<earthquake_detail>> {

    private final String EarthquakeUrl = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=4&limit=10";
    private CustomAdapter mAdapter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        boolean isConnected = (networkInfo != null) && networkInfo.isConnectedOrConnecting();
        if(!isConnected){
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.INVISIBLE);
            ListView listView = (ListView) findViewById(R.id.list);
            TextView emptyView = (TextView) findViewById(R.id.emptyView);
            emptyView.setText("No Internet Connection");
            listView.setEmptyView(emptyView);
        }
        else{
            getLoaderManager().initLoader(0, null, this);
        }

    }


    @Override
    public Loader<ArrayList<earthquake_detail>> onCreateLoader(int id, Bundle args) {
        Log.v("EarthquakeActivity","onCreateLoader() method called.");
        return new EarthquakeLoader(this, EarthquakeUrl);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<earthquake_detail>> loader, ArrayList<earthquake_detail> data) {

        Log.v("EarthquakeLoader","Loading finished.");
        ListView list = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        if(data == null){
            TextView emptyView = (TextView) findViewById(R.id.emptyView);
            emptyView.setText("No results Found");
            list.setEmptyView(emptyView);
        }
        else {
            mAdapter = new CustomAdapter(this, data);
            list.setAdapter(mAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    earthquake_detail object = (earthquake_detail) parent.getItemAtPosition(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(object.getUrl()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<earthquake_detail>> loader) {
        Log.v("EarthquakeActivity","Loader reset.");

    }
}
