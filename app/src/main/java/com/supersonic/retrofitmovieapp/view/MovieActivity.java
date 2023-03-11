package com.supersonic.retrofitmovieapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.supersonic.retrofitmovieapp.R;
import com.supersonic.retrofitmovieapp.databinding.ActivityMovieBinding;
import com.supersonic.retrofitmovieapp.model.Result;

public class MovieActivity extends AppCompatActivity {

    private ActivityMovieBinding activityMovieBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        activityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        TextView title = findViewById(R.id.movieTitleTextView);
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("result")){

            Result result = intent.getParcelableExtra("result");

            activityMovieBinding.setResult(result);

            setTitle(result.getTitle());

            if (result.getTitle().equals(result.getOriginalTitle())){
                title.setText(result.getTitle());
            } else {
                String titleString = result.getTitle() + "\n" + "(" + result.getOriginalTitle() + ")";
                title.setText(titleString);
            }

        }


    }
}