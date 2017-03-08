package com.sadek.apps.popularmoviestage1.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud Sadek on 3/6/2017.
 */
public class ParseMovieVedios {
    String key, content;

    public static final String JSON_ARRAY = "results";
    public static final String KEY_KEY = "key";
    public static final String KEY_CONTENT = "content";

    private JSONArray movieArray = null;
    private JSONArray reviewArray = null;

    private String json;

    public ParseMovieVedios(String json) {
        this.json = json;
    }

    public List<String> parseVedios() {
        JSONObject jsonObject = null;
        List<String> videos = new ArrayList<String>();
        try {
            jsonObject = new JSONObject(json);
            movieArray = jsonObject.getJSONArray(JSON_ARRAY);
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject jo = movieArray.getJSONObject(i);
                key = jo.getString(KEY_KEY);
                videos.add(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }

    public List<String> parseReviews() {
        JSONObject jsonObject = null;
        List<String> reviews = new ArrayList<String>();
        try {
            jsonObject = new JSONObject(json);
            reviewArray = jsonObject.getJSONArray(JSON_ARRAY);
            for (int i = 0; i < reviewArray.length(); i++) {
                JSONObject jo = reviewArray.getJSONObject(i);
                content = jo.getString(KEY_CONTENT);
                reviews.add(content);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
