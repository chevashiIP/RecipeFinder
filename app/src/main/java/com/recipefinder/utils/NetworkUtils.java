package com.recipefinder.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import com.recipefinder.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public final class NetworkUtils {

    private static final String SpoonacularURL = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";

    private static final String recipystr = "recipes";
    private static final String searchstr = "search";
    private static final String querystr = "query";

    public static Request buildUrl(Context context, String searchquerry){
        String api_key = context.getString(R.string.API_KEY);

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .addPathSegment("recipes")
                .addPathSegment("search")
                .addQueryParameter("query", searchquerry)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", api_key)
                .build();


        return request;
    }

    public static String getResponse(Request request){
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try{
            response = client.newCall(request).execute();
        } catch (IOException e){
            e.printStackTrace();
        }

        String jsonData = null;
        try{
            jsonData = response.body().string();
        } catch (IOException e){
            e.printStackTrace();
        }

        return jsonData;
    }
}
