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
}
