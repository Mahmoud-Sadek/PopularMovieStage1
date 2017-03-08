package com.sadek.apps.popularmoviestage1.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sadek.apps.popularmoviestage1.R;
import com.sadek.apps.popularmoviestage1.adapter.MoviesAdapter;
import com.sadek.apps.popularmoviestage1.database.FavoriteAdapter;
import com.sadek.apps.popularmoviestage1.model.Movie;
import com.sadek.apps.popularmoviestage1.parse.ParseMovie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String JSON_URL_popular = "http://api.themoviedb.org/3/movie/popular?api_key=9490ec35a6eea2efe32378982073f7a3";
    final String JSON_URL_toprated = "http://api.themoviedb.org/3/movie/top_rated?api_key=9490ec35a6eea2efe32378982073f7a3";
    final String URL_KEY = "url";
    SharedPreferences sp;
    RecyclerView mMoviesRecyclerView;
    public static boolean largeScreen;
    private String JSON_URL_favorite = "-";

    //    72659fcbe6b80e24ac36-dd5bbdbe316"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mMoviesRecyclerView.setHasFixedSize(true);
        //Set RecyclerView type according to intent value
        mMoviesRecyclerView.setLayoutManager(new GridLayoutManager(this, calculateNoOfColumns(getBaseContext())));
        checkScreeen();
        // getData from json
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String URL = sp.getString(URL_KEY, null);
        if (URL == null) {
            SharedPreferences.Editor spe = sp.edit();
            spe.putString(URL_KEY, JSON_URL_popular).apply();
            spe.commit();
            sendRequest(JSON_URL_popular);

        } else if (URL.equals("-")) {
            fav();
        } else {
            sendRequest(URL);
        }

    }

    private void checkScreeen() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        if (size.x > size.y || screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            View viewGroup = (View) findViewById(R.id.fragment2);
            viewGroup.setVisibility(View.INVISIBLE);
            largeScreen = true;
        } else {
            largeScreen = false;
        }
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_movie clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            sendRequest(JSON_URL_popular);
            SharedPreferences.Editor spe = sp.edit();
            spe.putString(URL_KEY, JSON_URL_popular).apply();
            spe.commit();
            return true;
        }
        if (id == R.id.action_top_rated) {
            sendRequest(JSON_URL_toprated);
            SharedPreferences.Editor spe = sp.edit();
            spe.putString(URL_KEY, JSON_URL_toprated).apply();
            spe.commit();
            return true;
        }
        if (id == R.id.action_favorite) {
            SharedPreferences.Editor spe = sp.edit();
            spe.putString(URL_KEY, JSON_URL_favorite).apply();
            spe.commit();
            fav();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fav() {
        Toast.makeText(getBaseContext(), "Favorite", Toast.LENGTH_LONG).show();
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getBaseContext());
        ArrayList<Movie> movieData = favoriteAdapter.getAllData();
        MoviesAdapter adapter = new MoviesAdapter(getBaseContext(), movieData);
        mMoviesRecyclerView.setAdapter(adapter);
    }

    private void sendRequest(String JSON_URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Data Not Loaded", Toast.LENGTH_LONG).show();
                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {
        ParseMovie pj = new ParseMovie(json);
        MoviesAdapter adapter = new MoviesAdapter(getBaseContext(), pj.parseMovies());
        mMoviesRecyclerView.setAdapter(adapter);

    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}
