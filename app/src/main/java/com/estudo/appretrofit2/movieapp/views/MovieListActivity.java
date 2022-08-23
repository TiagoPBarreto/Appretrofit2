package com.estudo.appretrofit2.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.estudo.appretrofit2.R;
import com.estudo.appretrofit2.movieapp.adapters.MovieRecyclerView;
import com.estudo.appretrofit2.movieapp.adapters.OnMovieListener;
import com.estudo.appretrofit2.movieapp.models.MovieModel;
import com.estudo.appretrofit2.movieapp.request.Servicey;
import com.estudo.appretrofit2.movieapp.response.MovieSearchResponse;
import com.estudo.appretrofit2.movieapp.utils.Credentials;
import com.estudo.appretrofit2.movieapp.utils.MovieApi;
import com.estudo.appretrofit2.movieapp.viewmodel.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    //Before we run our app, we need to add the network security


    //viewModel
    private MovieListViewModel mMovieListViewModel;

    //RecyclerView
    private RecyclerView mRecyclerView;
    private MovieRecyclerView mMovieRecyclerViewAdapter;

    boolean isPopular = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SearchView
        SetupSearchView();




        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();

        // Calling the observers
        ObserveAnyChange();
        ObservePopularMovies();

        //Getting popular movies
        mMovieListViewModel.searchMoviePop(1);





    }

    private void ObservePopularMovies() {

        mMovieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing any data change

                if (movieModels != null){

                    for (MovieModel movieModel:movieModels){

                        // Get rhe data in log
                        Log.v("Tagy","onChanged: "+movieModel.getTitle());
                        mMovieRecyclerViewAdapter.setMovies(movieModels);

                    }

                }

            }
        });


    }


    // Observing any data change
    private void ObserveAnyChange(){

        mMovieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing any data change

                if (movieModels != null){

                    for (MovieModel movieModel:movieModels){

                        // Get rhe data in log
                        Log.v("Tagy","onChanged: "+movieModel.getTitle());
                        mMovieRecyclerViewAdapter.setMovies(movieModels);

                    }

                }

            }
        });

    }



    //5 - Intializing recyclerview & adding data to it

    private void ConfigureRecyclerView(){

        mMovieRecyclerViewAdapter = new MovieRecyclerView(this);
        mRecyclerView.setAdapter(mMovieRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        //Recyclerview Pagination ou loading next page api response

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){

                    // here we need display the next search results on the next page of api
                    mMovieListViewModel.searchNextPage();

                }

            }
        });

    }

    @Override
    public void onMovieClick(int position) {

       // Toast.makeText(this, "the position "+position, Toast.LENGTH_SHORT).show();

        //we need the id of movie in order to get all it`s details
        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie",mMovieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMovieListViewModel.searchMovieApi(

                        //the search string getted from searchview
                        query,
                        1

                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPopular = false;
            }
        });

    }

    //


}