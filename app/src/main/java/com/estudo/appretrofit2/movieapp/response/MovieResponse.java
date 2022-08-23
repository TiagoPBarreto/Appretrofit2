package com.estudo.appretrofit2.movieapp.response;

import com.estudo.appretrofit2.movieapp.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// obter somente um filme
public class MovieResponse {
    // 1 - Finding the movie object

    @SerializedName("results")
    @Expose
    private MovieModel mMovie;

    public MovieModel getMovie(){
        return mMovie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "mMovie=" + mMovie +
                '}';
    }
}
