package com.example.brewersnotepad.mobile.data;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class RecipeManager {

    private static RecipeDataHolder currentRecipe;


    public static RecipeDataHolder getCurrentRecipe() {
        return currentRecipe;
    }
    public static void setCurrentRecipe(RecipeDataHolder recipe) {
        currentRecipe = recipe;
    }

}
