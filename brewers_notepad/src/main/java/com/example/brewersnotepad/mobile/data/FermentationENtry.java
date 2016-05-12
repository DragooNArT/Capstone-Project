package com.example.brewersnotepad.mobile.data;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class FermentationEntry {

    private String phaseName;

    private int phaseDuration;

    private int targetPhaseTemp;

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

    public int getTargetPhaseTemp() {
        return targetPhaseTemp;
    }

    public void setTargetPhaseTemp(int targetPhaseTemp) {
        this.targetPhaseTemp = targetPhaseTemp;
    }
}