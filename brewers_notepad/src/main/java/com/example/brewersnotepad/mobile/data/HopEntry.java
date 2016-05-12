package com.example.brewersnotepad.mobile.data;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class HopEntry {

    private String hopVariety;

    private String hopType;

    private int timeToAdd;

    private int hopQuantity;

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

    public int getHopQuantity() {
        return hopQuantity;
    }

    public void setHopQuantity(int hopQuantity) {
        this.hopQuantity = hopQuantity;
    }
}