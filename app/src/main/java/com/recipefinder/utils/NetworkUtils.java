package com.recipefinder.utils;

import android.content.Context;

import com.recipefinder.R;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public final class NetworkUtils {

    private static final String SpoonacularURL = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private static final String host = "x-rapidapi-host";
    private static final String rapidkey ="x-rapidapi-key";

    private static final String recipystr = "recipes";
    private static final String randomstr = "random";
    private static final String searchstr = "search";
    private static final String querystr = "query";
    private static final String numberstr = "number";
    private static final String informstr = "information";
    private static final String inclNutrition = "includeNutrition";

    public static Request buildSearchUrl(Context context, String searchquerry){
        String api_key = context.getString(R.string.API_KEY);

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(SpoonacularURL)
                .addPathSegment(recipystr)
                .addPathSegment(searchstr)
                .addQueryParameter(querystr, searchquerry)
                .addQueryParameter(numberstr, "99")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader(host, SpoonacularURL)
                .addHeader(rapidkey, api_key)
                .build();


        return request;
    }

    public static Request buildRandomUrl(Context context){
        String api_key = context.getString(R.string.API_KEY);

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(SpoonacularURL)
                .addPathSegment(recipystr)
                .addPathSegment(randomstr)
                .addQueryParameter(numberstr, "10")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader(host, SpoonacularURL)
                .addHeader(rapidkey, api_key)
                .build();


        return request;
    }

    public static Request buildRecipeUrl(Context context, String recipeid){
        String api_key = context.getString(R.string.API_KEY);

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host(SpoonacularURL)
                .addPathSegment(recipystr)
                .addPathSegment(recipeid)
                .addPathSegment(informstr)
                .addQueryParameter(inclNutrition, "true")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader(host, SpoonacularURL)
                .addHeader(rapidkey, api_key)
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
