package com.estudo.appretrofit2.movieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.estudo.appretrofit2.movieapp.models.MovieModel;
import com.estudo.appretrofit2.movieapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    //  this class is used for viewModel

    private MovieRepository mMovieRepository;

    //Construtor


    public MovieListViewModel() {

        mMovieRepository = MovieRepository.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies(){

        return mMovieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getPop(){

        return mMovieRepository.getPop();
    }

    // 3 - Calling method in viewModel
    public void searchMovieApi(String query, int pageNumber){

        mMovieRepository.searchMovieApi(query, pageNumber);

    }

    public void searchMoviePop(int pageNumber){

        mMovieRepository.searchMoviePop(pageNumber);

    }

    public void searchNextPage(){

        mMovieRepository.searchNextPage();

    }

}
