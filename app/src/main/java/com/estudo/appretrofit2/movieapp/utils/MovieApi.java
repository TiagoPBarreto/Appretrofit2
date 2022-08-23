package com.estudo.appretrofit2.movieapp.utils;

import com.estudo.appretrofit2.movieapp.models.MovieModel;
import com.estudo.appretrofit2.movieapp.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    //Search for movies
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(

            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );


    // Get popular movie
    @GET("/3/movie/popular")
    Call<MovieSearchResponse>getPopular(

            @Query("api_key")String key,
            @Query("page") int page

    );


    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(

            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
}
