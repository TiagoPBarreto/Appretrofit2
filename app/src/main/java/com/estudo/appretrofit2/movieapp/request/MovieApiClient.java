package com.estudo.appretrofit2.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.estudo.appretrofit2.movieapp.AppExecutors;
import com.estudo.appretrofit2.movieapp.models.MovieModel;
import com.estudo.appretrofit2.movieapp.response.MovieSearchResponse;
import com.estudo.appretrofit2.movieapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //Live data for search
    private MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    //making Global runnable
    private RetrieveMoviesRunnable mRetrieveMoviesRunnable;

    //Live data for popular
    private MutableLiveData<List<MovieModel>>mMoviesPop;

    //making Popular runnable
    private RetrieveMoviesRunnablePop mRetrieveMoviesRunnablePOP;



    public static MovieApiClient getInstance() {

        if (instance == null) {

            instance = new MovieApiClient();

        }
        return instance;
    }

    private MovieApiClient() {

        mMovies = new MutableLiveData<>();
        mMoviesPop = new MediatorLiveData<>();
    }



    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop() {
        return mMoviesPop;
    }

    //this mothod that we are going to call through the classes
    public void searchMovieApi(String query, int pageNumber) {

        if (mRetrieveMoviesRunnable != null) {
            mRetrieveMoviesRunnable = null;

        }
        mRetrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(mRetrieveMoviesRunnable);


        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);


    }

    public void searchMoviePop( int pageNumber) {

        if (mRetrieveMoviesRunnablePOP != null) {
            mRetrieveMoviesRunnablePOP = null;

        }
        mRetrieveMoviesRunnablePOP = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().networkIO().submit(mRetrieveMoviesRunnablePOP);


        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // cancelling the retrofit call
                myHandler2.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);


    }

    // retrieving data from RESTApi by runnable class
    // We have 2 typos of queries : the id & search queries
    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {

                    return;
                }
                if (response.code() == 200) {

                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {

                        // sending data to live data
                        //PostValues: used for background thread
                        //SetValue: not for background thread
                        mMovies.postValue(list);

                    } else {

                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }

                } else {

                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMovies.postValue(null);


                }


            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }


            if (cancelRequest) {
                return;
            }

        }

        // Search method query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {

            return Servicey.getMovieApi().searchMovie(

                    Credentials.API_KEY,
                    query,
                    pageNumber

            );

        }

        private void cancelRequest() {

            Log.v("Tag", "Cancelling search Request");
            cancelRequest = true;

        }
    }
    private class RetrieveMoviesRunnablePop implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop (int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //Getting the response objects
            try {
                Response response2 = getPop(pageNumber).execute();
                if (cancelRequest) {

                    return;
                }
                if (response2.code() == 200) {

                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response2.body()).getMovies());
                    if (pageNumber == 1) {

                        // sending data to live data
                        //PostValues: used for background thread
                        //SetValue: not for background thread
                        mMoviesPop.postValue(list);

                    } else {

                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }

                } else {

                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMovies.postValue(null);


                }


            } catch (IOException e) {
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }


            if (cancelRequest) {
                return;
            }

        }

        // Search method query
        private Call<MovieSearchResponse> getPop(int pageNumber) {

            return Servicey.getMovieApi().getPopular(

                    Credentials.API_KEY,
                    pageNumber

            );

        }

        private void cancelRequest() {

            Log.v("Tag", "Cancelling search Request");
            cancelRequest = true;

        }
    }
}
