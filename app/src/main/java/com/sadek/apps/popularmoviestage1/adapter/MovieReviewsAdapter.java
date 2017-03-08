package com.sadek.apps.popularmoviestage1.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sadek.apps.popularmoviestage1.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Mahmoud Sadek on 3/6/2017.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MyViewHolder> {

    private List<String> reviewsList;
    private Context mContext;
    private int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        CardView mCardView;

        public MyViewHolder(View view) {
            super(view);
            txt_title = (TextView) view.findViewById(R.id.review);
            mCardView = (CardView) view.findViewById(R.id.card_view);

        }
    }


    public MovieReviewsAdapter(Context mContext, List<String> reviewsList) {
        this.mContext = mContext;
        this.reviewsList = reviewsList;
    }

    View itemView;

    @Override
    public MovieReviewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);

        return new MovieReviewsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieReviewsAdapter.MyViewHolder holder, final int position) {
        final String review_item = reviewsList.get(position);

            setAnimation(holder.mCardView, position);

            holder.txt_title.setText(review_item);
    }

    @Override
    public void onBindViewHolder(MovieReviewsAdapter.MyViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
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
