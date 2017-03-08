package com.sadek.apps.popularmoviestage1.parse;

import com.sadek.apps.popularmoviestage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud Sadek on 2/16/2017.
 */
public class ParseMovie {
    String original_title, poster_image, overview, vote_average, release_date, id;

    public static final String JSON_ARRAY = "results";
    public static final String KEY_ORIGINAL_TITLE = "original_title";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_VOTE_AVERAGE = "vote_average";
    public static final String KEY_RELEASE_DATE = "release_date";
    public static final String KEY_ID = "id";

    private JSONArray movieArray = null;

    private String json;

    public ParseMovie(String json) {
        this.json = json;
    }

    public List<Movie> parseMovies() {
        JSONObject jsonObject = null;
        List<Movie> movies = new ArrayList<Movie>();
        try {
            jsonObject = new JSONObject(json);
            movieArray = jsonObject.getJSONArray(JSON_ARRAY);
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject jo = movieArray.getJSONObject(i);
                original_title = jo.getString(KEY_ORIGINAL_TITLE);
                poster_image = jo.getString(KEY_POSTER_PATH);
                overview = jo.getString(KEY_OVERVIEW);
                vote_average = jo.getString(KEY_VOTE_AVERAGE);
                release_date = jo.getString(KEY_RELEASE_DATE);
                id = jo.getString(KEY_ID);
                Movie mMovie = new Movie(original_title, poster_image, overview, vote_average, release_date, id);
                movies.add(mMovie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
