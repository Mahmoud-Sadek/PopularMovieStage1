package com.sadek.apps.popularmoviestage1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sadek.apps.popularmoviestage1.R;
import com.sadek.apps.popularmoviestage1.adapter.MovieReviewsAdapter;
import com.sadek.apps.popularmoviestage1.adapter.MovieVideosAdapter;
import com.sadek.apps.popularmoviestage1.database.FavoriteAdapter;
import com.sadek.apps.popularmoviestage1.model.Movie;
import com.sadek.apps.popularmoviestage1.parse.ParseMovieVedios;
import com.squareup.picasso.Picasso;

/**
 * Created by Mahmoud Sadek on 3/8/2017.
 */

public class DetailActivityFragment extends Fragment {

    static TextView mTitle_text;
    static TextView mDate_text;
    static TextView mVote_text;
    static TextView mOverview_text;
    static ImageView mPoster_img;
    public static Movie movie;
    static RecyclerView mVideosRecyclerView;
    static RecyclerView mReviewsRecyclerView;
    static Context mContext;
    public static View view;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        mTitle_text = (TextView) view.findViewById(R.id.txt_title);
        mDate_text = (TextView) view.findViewById(R.id.txt_date);
        mVote_text = (TextView) view.findViewById(R.id.txt_vote);
        mOverview_text = (TextView) view.findViewById(R.id.txt_overview);
        mPoster_img = (ImageView) view.findViewById(R.id.img_poster);
        mContext = getContext();
        view.findViewById(R.id.btn_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favBtn();
            }
        });
        mVideosRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_video);
        mVideosRecyclerView.setHasFixedSize(true);
        //Set RecyclerView type according to intent value
        mVideosRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));

        mReviewsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_reviews);
        mReviewsRecyclerView.setHasFixedSize(true);
        //Set RecyclerView type according to intent value
        mReviewsRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
        if (movie.getOriginal_title() == null) {
            view.setVisibility(View.INVISIBLE);
            return view;
        } else {
            view.setVisibility(View.VISIBLE);
            initData();
        }
        return view;
    }

    public static void initData() {
        setData();
        sendRequest("http://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=9490ec35a6eea2efe32378982073f7a3");
        sendRequest("http://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews?api_key=9490ec35a6eea2efe32378982073f7a3");


    }


    public static void favBtn() {
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(mContext);
        if (favoriteAdapter.getData(movie.getId()) == null) {
            long insrt = favoriteAdapter.insertData(movie.getId(), movie.getPoster_image(), movie.getOriginal_title(), movie.getRelease_date(), movie.getVote_average(), movie.getOverview());
            if (insrt != -1) {
                Toast.makeText(mContext, "Favorite", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(mContext, "Error In Insert Data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Favorite Already", Toast.LENGTH_SHORT).show();
        }
    }

    private static void setData() {
        mTitle_text.setText(movie.getOriginal_title());
        mDate_text.setText(movie.getRelease_date());
        mVote_text.setText(movie.getVote_average());
        mOverview_text.setText(movie.getOverview());
        String baseUrl = "http://image.tmdb.org/t/p/w185";
        Picasso.with(mContext).load(baseUrl + movie.getPoster_image()).error(R.drawable.image).into(mPoster_img);
    }


    private static void sendRequest(final String JSON_URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("video", "onResponse: " + response);
                        if (JSON_URL.contains("videos")) {
                            showJSON(response, "video");
                        } else {
                            showJSON(response, "review");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Not Loaded", Toast.LENGTH_LONG).show();
                    }
                }) {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    private static void showJSON(String json, String type) {
        ParseMovieVedios pj = new ParseMovieVedios(json);

        if (type.equals("video")) {
            MovieVideosAdapter adapter = new MovieVideosAdapter(mContext, pj.parseVedios());
            mVideosRecyclerView.setAdapter(adapter);
        } else {
            MovieReviewsAdapter adapter2 = new MovieReviewsAdapter(mContext, pj.parseReviews());
            mReviewsRecyclerView.setAdapter(adapter2);
        }

    }
}
