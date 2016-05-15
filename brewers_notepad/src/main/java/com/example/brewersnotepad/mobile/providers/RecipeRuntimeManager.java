package com.example.brewersnotepad.mobile.providers;

import com.example.brewersnotepad.mobile.data.RecipeDataHolder;

import java.util.List;

/**
 * Created by xnml on 12.5.2016 г..
 */
public class RecipeRuntimeManager {

    private static RecipeDataHolder currentRecipe;
    private static List<RecipeDataHolder> recipesList;

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
    public static void setRecipesList(List<RecipeDataHolder> recipesList) {
        RecipeRuntimeManager.recipesList = recipesList;
    }
}
