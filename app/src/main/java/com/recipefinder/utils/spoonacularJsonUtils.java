package com.recipefinder.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class spoonacularJsonUtils {
    public static SearchData[] getRecipe(String jsonData){
        JSONArray jsonArray = null;
        try {
            JSONObject jobject = new JSONObject(jsonData);
            jsonArray = jobject.getJSONArray("results");
        }catch (JSONException e){
            e.printStackTrace();
        }

        SearchData[] data = new SearchData[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("id");
                String title = object.getString("title");
                String imageURL = object.getString("image");
                SearchData newData = new SearchData(id,title,imageURL);
                data[i] = newData;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static SearchData[] getRandomRecipe(String jsonData){
        JSONArray jsonArray = null;
        try {
            JSONObject jobject = new JSONObject(jsonData);
            jsonArray = jobject.getJSONArray("recipes");
        }catch (JSONException e){
            e.printStackTrace();
        }

        SearchData[] data = new SearchData[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                int id = object.getInt("id");
                String title = object.getString("title");
                String imageURL = object.getString("image");
                SearchData newData = new SearchData(id,title,imageURL);
                data[i] = newData;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static RecipeData getRecipeInfo(String jsonData){
        JSONArray jsonArray = null;
        int id = 0;
        String title = null;
        String imageURL = null;
        int readytime = 0;
        String sourceURL = null;
        String[] instructions = null;
        String[] ingridients = null;
        String[] inggridientsimg = null;
        String[] ingammount = null;
        String[] ingunit = null;
        try {
            JSONObject jobject = new JSONObject(jsonData);
            id = jobject.getInt("id");
            title = jobject.getString("title");
            imageURL = jobject.getString("image");
            readytime = jobject.getInt("readyInMinutes");
            sourceURL = jobject.getString("sourceUrl");

            jsonArray = jobject.getJSONArray("analyzedInstructions");
            JSONObject zeroarray = jsonArray.getJSONObject(0);
            JSONArray steps = zeroarray.getJSONArray("steps");
            instructions = new String[steps.length()];
            for(int i = 0; i < steps.length(); i++){
                JSONObject object = steps.getJSONObject(i);
                instructions[i] = object.getString("step");
            }

            JSONArray ingridientsobj = jobject.getJSONArray("extendedIngredients");
            ingridients = new String[ingridientsobj.length()];
            inggridientsimg = new String[ingridientsobj.length()];
            ingammount = new String[ingridientsobj.length()];
            ingunit = new String[ingridientsobj.length()];


            for (int i = 0; i < ingridientsobj.length(); i++){
                JSONObject sobject = ingridientsobj.getJSONObject(i);
                ingridients[i] = sobject.getString("name");
                inggridientsimg[i] = sobject.getString("image");
                ingammount[i] = sobject.getString("amount");
                ingunit[i] = sobject.getString("unit");
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        RecipeData newData = new RecipeData(id, title,imageURL,readytime,
                sourceURL, ingridients, inggridientsimg,
                ingunit, ingammount, instructions);
        return newData;
    }


    public static class SearchData {
        int id;
        String title;
        String imageURL;

        public SearchData(int id, String title, String imageURL){
            this.id = id;
            this.title = title;
            this.imageURL = imageURL;
        }

        public int getID(){
            return id;
        }

        public String getTitle(){ return title; }

        public String getImageURL(){
            return imageURL;
        }

    }

    public static class RecipeData{
        int id;
        String title;
        String imageURL;
        int readyTime;
        String sourceURL;
        String[] IngredientName;
        String[] IngredientImg;
        String[] ingredientunit;
        String[] ingredientammount;
        String[] Instructions;

        public RecipeData(int id, String title, String imageURL, int readyTime,
                          String sourceURL, String[] IngredientName, String[] IngredientImg,
                          String[] ingredientunit, String[] ingredientammount, String[] instructions){
            this.id = id;
            this.title = title;
            this.imageURL = imageURL;
            this.readyTime = readyTime;
            this.sourceURL = sourceURL;
            this.IngredientName = IngredientName;
            this.IngredientImg = IngredientImg;
            this.ingredientunit = ingredientunit;
            this.ingredientammount = ingredientammount;
            this.Instructions = instructions;
        }

        public int getId() { return id;  }

        public String getTitle(){ return title; }

        public String getImageURL(){
            return imageURL;
        }

        public int getReadyTime(){return readyTime;}

        public String getSourceURL(){return sourceURL;}

        public String[] getIngredientName(){return IngredientName;}

        public String[] getIngredientImg(){return IngredientImg;}

        public String[] getIngredientUnit(){return ingredientunit;}

        public String[] getIngredientamount(){return ingredientammount;}

        public String[] getInstructions(){return Instructions;}
    }
}
