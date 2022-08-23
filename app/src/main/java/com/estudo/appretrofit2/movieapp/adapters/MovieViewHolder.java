package com.estudo.appretrofit2.movieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.estudo.appretrofit2.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    ImageView imageView;
    RatingBar mRatingBar;

    //Click
    OnMovieListener mOnMovieListener;

    public MovieViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {
        super(itemView);
        this.mOnMovieListener = onMovieListener;


        imageView = itemView.findViewById(R.id.movie_img);
        mRatingBar = itemView.findViewById(R.id.rating_bar);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        mOnMovieListener.onMovieClick(getAdapterPosition());

    }
}
