package com.example.ryder.earthquakereport;

import java.util.Date;

public class earthquake_detail {

    private double mMagnitude;

    private String mPlace;

    private long mtimeInMilliseconds;

    private String mUrl;

    public earthquake_detail(double mag, String place, long mtimeInMilliseconds, String Url){
        mMagnitude = mag;
        mPlace = place;
        this.mtimeInMilliseconds = mtimeInMilliseconds;
        mUrl = Url;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getPlace() {
        return mPlace;
    }

    public long gettimeInMilliseconds() {
        return mtimeInMilliseconds;
    }

    public String getUrl() {
        return mUrl;
    }
}
