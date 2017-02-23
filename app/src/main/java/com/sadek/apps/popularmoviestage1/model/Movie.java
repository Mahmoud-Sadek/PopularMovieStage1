package com.sadek.apps.popularmoviestage1.model;

/**
 * Created by Mahmoud Sadek on 2/16/2017.
 */
public class Movie {
    String original_title, poster_image, overview, vote_average, release_date;

    public Movie(String original_title, String poster_image, String overview, String vote_average, String release_date) {
        this.original_title = original_title;
        this.poster_image = poster_image;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public Movie() {
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }
}
