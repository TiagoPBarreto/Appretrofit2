package com.estudo.appretrofit2.movieapp.response;

import com.estudo.appretrofit2.movieapp.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// class para obter varios filmes (movies list) - popular movies

public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    public int getTotal_count(){

        return total_count;
    }
    public List<MovieModel>getMovies(){

        return movies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}';
    }
}
