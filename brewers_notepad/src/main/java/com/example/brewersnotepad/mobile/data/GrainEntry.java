package com.example.brewersnotepad.mobile.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class GrainEntry implements Parcelable {

    private String grainType;

    private double grainQuantity;
    public GrainEntry() {

    }
    protected GrainEntry(Parcel in) {
        grainType = in.readString();
        grainQuantity = in.readDouble();
    }

    public static final Creator<GrainEntry> CREATOR = new Creator<GrainEntry>() {
        @Override
        public GrainEntry createFromParcel(Parcel in) {

            return new GrainEntry(in);
        }

        @Override
        public GrainEntry[] newArray(int size) {
            return new GrainEntry[size];
        }
    };

    public String getGrainType() {
        return grainType;
    }

    public void setGrainType(String grainType) {
        this.grainType = grainType;
    }

    public double getGrainQuantity() {
        return grainQuantity;
    }

    public void setGrainQuantity(double grainQuantity) {
        this.grainQuantity = grainQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(grainType);
            parcel.writeDouble(grainQuantity);
    }
}