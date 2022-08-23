package com.estudo.appretrofit2.movieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.estudo.appretrofit2.movieapp.models.MovieModel;
import com.estudo.appretrofit2.movieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    // this class is acting as repository

    private static MovieRepository instance;


    private MovieApiClient mMovieApiClient;

    private String mQuery;
    private int mPgeNumber;

    public static MovieRepository getInstance() {

        if (instance == null) {

            instance = new MovieRepository();

        }
        return instance;
    }

    private MovieRepository() {


        //  mMovies = new MutableLiveData<>();
        mMovieApiClient = MovieApiClient.getInstance();

    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>> getPop() {
        return mMovieApiClient.getMoviesPop();
    }

    // 2 - Calling the method in Repository
    public void searchMovieApi(String query, int pageNumber){
        mQuery = query;
        mPgeNumber = pageNumber;
        mMovieApiClient.searchMovieApi(query,pageNumber);

    }

    public void searchMoviePop(int pageNumber){
        mPgeNumber = pageNumber;
        mMovieApiClient.searchMoviePop(pageNumber);

    }

    public void searchNextPage(){

        searchMovieApi(mQuery,mPgeNumber+1);


    }


}
