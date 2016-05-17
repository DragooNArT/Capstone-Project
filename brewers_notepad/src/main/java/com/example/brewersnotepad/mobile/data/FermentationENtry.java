package com.example.brewersnotepad.mobile.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class FermentationEntry implements Parcelable {

    private String phaseName;

    private int phaseDuration;

    private double targetPhaseTemp;
    public FermentationEntry() {

    }
    protected FermentationEntry(Parcel in) {
        phaseName = in.readString();
        phaseDuration = in.readInt();
        targetPhaseTemp = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phaseName);
        dest.writeInt(phaseDuration);
        dest.writeDouble(targetPhaseTemp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FermentationEntry> CREATOR = new Creator<FermentationEntry>() {
        @Override
        public FermentationEntry createFromParcel(Parcel in) {
            return new FermentationEntry(in);
        }

        @Override
        public FermentationEntry[] newArray(int size) {
            return new FermentationEntry[size];
        }
    };

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public int getPhaseDuration() {
        return phaseDuration;
    }

    public void setPhaseDuration(int phaseDuration) {
        this.phaseDuration = phaseDuration;
    }

    public double getTargetPhaseTemp() {
        return targetPhaseTemp;
    }

    public void setTargetPhaseTemp(double targetPhaseTemp) {
        this.targetPhaseTemp = targetPhaseTemp;
    }
}