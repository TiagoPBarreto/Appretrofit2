package com.estudo.appretrofit2.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.estudo.appretrofit2.R;
import com.estudo.appretrofit2.movieapp.models.MovieModel;

public class MovieDetails extends AppCompatActivity {

    ImageView mImageViewDetails;
    TextView titleDetails, descDetails;
    RatingBar mRatingBarDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mImageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descDetails = findViewById(R.id.textView_desc_details);
        mRatingBarDetails = findViewById(R.id.ratingBar_details);
        
        GetDataFromIntent();
    }

    private void GetDataFromIntent() {

        if (getIntent().hasExtra("movie")){

            MovieModel movieModel = getIntent().getParcelableExtra("movie");
            Log.v("Tag","incoming intent "+movieModel.getTitle() );
        }

    }
}