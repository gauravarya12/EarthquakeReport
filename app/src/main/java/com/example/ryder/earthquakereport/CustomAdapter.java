package com.example.ryder.earthquakereport;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends ArrayAdapter<earthquake_detail> {

    public CustomAdapter(Context context, ArrayList<earthquake_detail> list){
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemview = convertView;
        if(listItemview == null){
            listItemview = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item,parent,false);
        }



        earthquake_detail earthquakeDetail = getItem(position);

        TextView mag_tv = (TextView) listItemview.findViewById(R.id.magnitude);
        GradientDrawable gradientDrawable = (GradientDrawable) mag_tv.getBackground();

        int colorResourceId = getMagnitudeColor(earthquakeDetail.getMagnitude());
        gradientDrawable.setColor(colorResourceId);

        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        String mag = decimalFormatter.format(earthquakeDetail.getMagnitude());
        mag_tv.setText(mag);

        TextView offset_tv = (TextView) listItemview.findViewById(R.id.offset);
        offset_tv.setText(getOffset(earthquakeDetail.getPlace()));

        TextView place_tv = (TextView) listItemview.findViewById(R.id.place);
        place_tv.setText(getPlace(earthquakeDetail.getPlace()));

        Date dateObject = new Date(earthquakeDetail.gettimeInMilliseconds());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        String date = dateFormatter.format(dateObject);
        TextView date_tv = (TextView) listItemview.findViewById(R.id.date);
        date_tv.setText(date);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String time = timeFormatter.format(dateObject);
        TextView time_tv = (TextView) listItemview.findViewById(R.id.time);
        time_tv.setText(time);



        return listItemview;
    }

    private String getOffset(String location){

        String s = "Near the";
        if(location.contains(",")){
            String [] words = location.split("of");
            s = words[0]+"of";
        }
        return s;

    }
    private String getPlace(String location){
        String s=location;
        if(location.contains(",")){
            String [] words = location.split("of");
            s = words[1];
        }

        return s.trim();
    }

    private int getMagnitudeColor(double mag){
        int mColorResourceid;
        int magFloor = (int) Math.floor(mag);
        switch (magFloor){
            case 0:
            case 1:
                mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude1);
                break;
            case 2:
                mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude2);
                break;
            case 3:
                mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude3);
                break;
            case 4:
                mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude4);
                break;
            case 5:
                mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude5);
                break;
            case 6:
                mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude6);
                break;
            case 7:
                mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude7);
                break;
            case 8:
                mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude8);
                break;
            case 9:
                mColorResourceid = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
                default:
                    mColorResourceid = ContextCompat.getColor(getContext(),R.color.magnitude10plus);
                    break;

        }
        return mColorResourceid;
    }
}
