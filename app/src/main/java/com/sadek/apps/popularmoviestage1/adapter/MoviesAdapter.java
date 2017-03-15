package com.sadek.apps.popularmoviestage1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sadek.apps.popularmoviestage1.R;
import com.sadek.apps.popularmoviestage1.activities.DetailActivity;
import com.sadek.apps.popularmoviestage1.activities.MainActivity;
import com.sadek.apps.popularmoviestage1.fragments.DetailActivityFragment;
import com.sadek.apps.popularmoviestage1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Mahmoud Sadek on 2/16/2017.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private final List<Movie> movieList;
    private Context mContext;
    private int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        ImageView img_poster;
        CardView mCardView;

        public MyViewHolder(View view) {
            super(view);
            txt_title = (TextView) view.findViewById(R.id.txt_item_title);
            img_poster = (ImageView) view.findViewById(R.id.img_item_poster);
            mCardView = (CardView) view.findViewById(R.id.card_view);

        }
    }


    public MoviesAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
        if (!movieList.isEmpty()) {
            DetailActivityFragment.movie = movieList.get(0);
        }
        if (MainActivity.largeScreen) {
            DetailActivityFragment.initData();
            DetailActivityFragment.view.setVisibility(View.VISIBLE);
        }
    }

    View itemView;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Movie movie_item = movieList.get(position);

        holder.txt_title.setText(movie_item.getOriginal_title());
        String baseUrl = "http://image.tmdb.org/t/p/w185";
        Picasso.with(mContext).load(baseUrl + movie_item.getPoster_image()).error(R.drawable.image).into(holder.img_poster);
        //add anim to card view
        setAnimation(holder.mCardView, position);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.largeScreen) {
                    DetailActivityFragment.movie = movie_item;
                    DetailActivityFragment.initData();
                    DetailActivityFragment.view.setVisibility(View.VISIBLE);
                }else {
                    DetailActivityFragment.movie = movie_item;
                    Intent n = new Intent(mContext, DetailActivity.class);
                    n.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(n);
                }
            }
        });

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated

        if (position > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.slide_down : R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}