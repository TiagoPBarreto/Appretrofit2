package com.estudo.appretrofit2.movieapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.estudo.appretrofit2.R;
import com.estudo.appretrofit2.movieapp.models.MovieModel;
import com.estudo.appretrofit2.movieapp.utils.Credentials;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener mOnMovieListener;

    private static final int DISPLAY_POP= 1;
    private static final int DISPLAY_SEARCH= 2;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        mOnMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == DISPLAY_SEARCH){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
            return new MovieViewHolder(view,mOnMovieListener);
        }
        else{

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movies_layout,parent,false);
            return new Popular_View_Holder(view,mOnMovieListener);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

     int itemViewType = getItemViewType(i);
     if (itemViewType == DISPLAY_SEARCH){

         // conta de 10 dividido por 2 para dar a votação maxima
         ((MovieViewHolder) holder).mRatingBar.setRating((mMovies.get(i).getVote_average()) / 2);

         //glide library biblioteca
         Glide.with(holder.itemView.getContext())
                 .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(i).getPoster_path())
                 .into(((MovieViewHolder) holder).imageView);
     }
     else{

         // conta de 10 dividido por 2 para dar a votação maxima
         ((Popular_View_Holder) holder).ratingBar22.setRating((mMovies.get(i).getVote_average()) / 2);

         //glide library biblioteca
         Glide.with(holder.itemView.getContext())
                 .load("https://image.tmdb.org/t/p/w500/" + mMovies.get(i).getPoster_path())
                 .into(((Popular_View_Holder) holder).imageviel22);
     }



    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();

        }
        return 0;

    }

    public void setMovies(List<MovieModel> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    // Getting the id of the movie clicked

    public MovieModel getSelectedMovie( int position ){

        if ( mMovies != null ){

            if ( mMovies.size() > 0 ){
                return mMovies.get(position);
            }
        }
        return null;

    }

    @Override
    public int getItemViewType(int position){

        if (Credentials.POPULAR){

            return DISPLAY_POP;
        }
        else {
            return DISPLAY_SEARCH;
        }
    }
}
