package com.sadek.apps.popularmoviestage1.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sadek.apps.popularmoviestage1.model.Movie;
import com.sadek.apps.popularmoviestage1.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView mTitle_text, mDate_text, mVote_text, mOverview_text;
    ImageView mPoster_img;
    public static Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        original_title, poster_image, overview, vote_average, release_date

        mTitle_text = (TextView) findViewById(R.id.txt_title);
        mDate_text = (TextView) findViewById(R.id.txt_date);
        mVote_text = (TextView) findViewById(R.id.txt_vote);
        mOverview_text = (TextView) findViewById(R.id.txt_overview);
        mPoster_img = (ImageView) findViewById(R.id.img_poster);

        setDate();
    }

    private void setDate() {
        mTitle_text.setText(movie.getOriginal_title());
        mDate_text.setText(movie.getRelease_date());
        mVote_text.setText(movie.getVote_average());
        mOverview_text.setText(movie.getOverview());
        String baseUrl = "http://image.tmdb.org/t/p/w185";
        Picasso.with(getBaseContext()).load(baseUrl + movie.getPoster_image()).error(R.drawable.image).into(mPoster_img);
    }

}
