package com.udacity.giannis.popularmovies.popularmovies.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.udacity.giannis.popularmovies.popularmovies.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giann on 3/4/2018.
 */

public class JsonUtils {

//in this class parse JSON data and add it as a Movie (parcable) oblect in the moviesArrayList.
    public static List<Movies> getStringInfoFromJson(String movieJsonString) throws JSONException {
        List<Movies> moviesArrayList = new ArrayList<>();
        JSONObject moviesData = new JSONObject(movieJsonString);
        JSONArray results = moviesData.getJSONArray("results");

        final int re = results.length();

        for (int i = 0; i < re; i++) {
            JSONObject firstObj = results.getJSONObject(i);
            String titlels = firstObj.getString("title");
            String image = firstObj.getString("poster_path");
            String vote_average = firstObj.getString("vote_average");
            String plot = firstObj.getString("overview");
            String release = firstObj.getString("release_date");
            String thumb = firstObj.getString("backdrop_path");
            if (titlels != null) {
                Movies movies = new Movies(titlels, image, vote_average, plot, release, thumb);
                moviesArrayList.add(movies);
            }
        }
        return moviesArrayList;
    }
}