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
public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MyViewHolder> {

    private List<String> videosList;
    private Context mContext;
    private int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        CardView mCardView;

        public MyViewHolder(View view) {
            super(view);
            txt_title = (TextView) view.findViewById(R.id.video);
            mCardView = (CardView) view.findViewById(R.id.card_view);

        }
    }


    public MovieVideosAdapter(Context mContext, List<String> videosList) {
        this.mContext = mContext;
        this.videosList = videosList;
    }

    View itemView;

    @Override
    public MovieVideosAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);

        return new MovieVideosAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieVideosAdapter.MyViewHolder holder, final int position) {
        final String video_item = videosList.get(position);
        holder.txt_title.setText("Trailer " + (position + 1));
        //add anim to card view
        setAnimation(holder.mCardView, position);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + video_item));
                n.addFlags(FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(n);
            }
        });
    }

    @Override
    public void onBindViewHolder(MovieVideosAdapter.MyViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return videosList.size();
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
