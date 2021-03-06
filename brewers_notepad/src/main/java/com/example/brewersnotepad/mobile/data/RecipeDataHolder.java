package com.example.brewersnotepad.mobile.data;

import android.net.Uri;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class RecipeDataHolder {

    private String recipe_name;

    private String recipe_type;

    private int mashDuration;

    private double mashTemp = Integer.MAX_VALUE;

    private int hopSteepDuration;

    private List<GrainEntry> recipe_grains = new ArrayList<GrainEntry>();

    private List<HopEntry>  recipe_hops =new ArrayList<HopEntry>();

    private List<FermentationEntry> fermentation_phases =new ArrayList<FermentationEntry>();


    public Uri getRecipeUri() {
        return recipeUri;
    }

    public void setRecipeUri(Uri recipeUri) {
        this.recipeUri = recipeUri;
    }

    private Uri recipeUri;

    public List<FermentationEntry> getFermentation_phases() {
        return fermentation_phases;
    }


    public void addFermentPhase(FermentationEntry ferPhase) {
        if(ferPhase != null) {
            this.fermentation_phases.add(ferPhase);
        }
    }

    public List<HopEntry> getRecipe_hops() {
        return recipe_hops;
    }

    public void addHops(HopEntry hops) {
        if(hops!=null) {
            recipe_hops.add(hops);
        }
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getRecipe_type() {
        return recipe_type;
    }

    public void setRecipe_type(String recipe_type) {
        this.recipe_type = recipe_type;
    }

    public int getMashDuration() {
        return mashDuration;
    }

    public void setMashDuration(int mashDuration) {
        this.mashDuration = mashDuration;
    }

    public double getMashTemp() {
        return mashTemp;
    }

    public void setMashTemp(double mashTemp) {
        this.mashTemp = mashTemp;
    }

    public int getHopSteepDuration() {
        return hopSteepDuration;
    }

    public void setHopSteepDuration(int hopSteepDuration) {
        this.hopSteepDuration = hopSteepDuration;
    }

    public List<GrainEntry> getRecipe_grains() {
        return recipe_grains;
    }

    public void addGrains(GrainEntry grains) {
        if(grains!=null) {
            this.recipe_grains.add(grains);
        }
    }
    @JsonIgnore
    public boolean isRecipeComplete() {

        if(getRecipe_name()== null || getRecipe_name().isEmpty()) {

            return false;
        }
        if(getFermentation_phases().isEmpty()) {

            return false;
        }
        if(getRecipe_hops().isEmpty()) {

            return false;
        }
        if(getRecipe_grains().isEmpty()) {

            return false;
        }
        if(getHopSteepDuration()<=0) {

            return false;
        }
        if(getMashTemp()==Integer.MAX_VALUE) {

            return false;
        }
        if(getMashDuration()<=0) {

            return false;
        }

        return true;
    }
}
