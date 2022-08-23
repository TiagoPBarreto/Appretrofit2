package com.estudo.appretrofit2.movieapp.request;



//metodo Singleton pattern

import com.estudo.appretrofit2.movieapp.utils.Credentials;
import com.estudo.appretrofit2.movieapp.utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicey {


    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static  Retrofit retrofit = retrofitBuilder.build();

    private static  MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){

        return movieApi;

    }


}
