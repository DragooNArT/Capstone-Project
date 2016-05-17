package com.example.brewersnotepad.mobile.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class HopEntry implements Parcelable {

    private String hopVariety;

    private String hopType;

    private int timeToAdd;

    private double hopQuantity;
    public HopEntry() {

    }
    protected HopEntry(Parcel in) {
        hopVariety = in.readString();
        hopType = in.readString();
        timeToAdd = in.readInt();
        hopQuantity = in.readInt();
    }

    public static final Creator<HopEntry> CREATOR = new Creator<HopEntry>() {
        @Override
        public HopEntry createFromParcel(Parcel in) {
            return new HopEntry(in);
        }

        @Override
        public HopEntry[] newArray(int size) {
            return new HopEntry[size];
        }
    };

    public String getHopVariety() {
        return hopVariety;
    }

    public void setHopVariety(String hopVariety) {
        this.hopVariety = hopVariety;
    }

    public String getHopType() {
        return hopType;
    }

    public void setHopType(String hopType) {
        this.hopType = hopType;
    }

    public int getTimeToAdd() {
        return timeToAdd;
    }

    public void setTimeToAdd(int timeToAdd) {
        this.timeToAdd = timeToAdd;
    }

    public double getHopQuantity() {
        return hopQuantity;
    }

    public void setHopQuantity(double hopQuantity) {
        this.hopQuantity = hopQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hopVariety);
        parcel.writeString(hopType);
        parcel.writeInt(timeToAdd);
        parcel.writeDouble(hopQuantity);
    }
}