package com.example.brewersnotepad.mobile.json;

import com.example.brewersnotepad.mobile.data.RecipeDataHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by DragooNArT-PC on 5/15/2016.
 */
public class JsonUtility {

    public static String ObjectToJson(Object instance) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RecipeDataHolder JsonToObject(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return  mapper.readValue(jsonData,RecipeDataHolder.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
