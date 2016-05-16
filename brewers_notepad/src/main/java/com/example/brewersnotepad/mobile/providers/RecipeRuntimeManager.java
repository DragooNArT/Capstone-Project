package com.example.brewersnotepad.mobile.providers;

import com.example.brewersnotepad.mobile.data.RecipeDataHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class RecipeRuntimeManager {

    private static RecipeDataHolder currentRecipe;
    private static RecipeDataHolder viewRecipe;
    private static List<RecipeDataHolder> recipesList = new ArrayList<RecipeDataHolder>();

    public static RecipeDataHolder getCurrentRecipe() {
        return currentRecipe;
    }
    public static void setCurrentRecipe(RecipeDataHolder recipe) {
        currentRecipe = recipe;
    }

    public static List<RecipeDataHolder>  getRecipesList() {
        return recipesList;
    }

    public static boolean  hasRecipes() {
        return recipesList != null && !recipesList.isEmpty();
    }

    public static RecipeDataHolder getRecipe(String recipeName) {
        if(recipeName!=null && !recipeName.isEmpty()) {
            for (RecipeDataHolder entry : recipesList) {
                if (entry.getRecipe_name().equals(recipeName)) {
                    return entry;
                }
            }
        }
        return null;
    }
    public static RecipeDataHolder getViewRecipe() {
        return viewRecipe;
    }
    public static void setViewRecipe(RecipeDataHolder viewRecipe) {
        RecipeRuntimeManager.viewRecipe = viewRecipe;
    }
}
