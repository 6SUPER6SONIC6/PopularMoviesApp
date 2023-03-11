package com.supersonic.retrofitmovieapp.model;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.supersonic.retrofitmovieapp.R;
import com.supersonic.retrofitmovieapp.service.MovieService;
import com.supersonic.retrofitmovieapp.service.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private ArrayList<Result> results = new ArrayList<>();
    private MutableLiveData<List<Result>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Result>> getMutableLiveData() {


        MovieService movieService = RetrofitInstance.getService();
        Call<MovieInfo> call = movieService.getPopularMovies(application.getApplicationContext().getString(R.string.api_key));

        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {

                MovieInfo movieInfo = response.body();

                if (movieInfo != null && movieInfo.getResults() != null){
                    results = (ArrayList<Result>) movieInfo.getResults();

                    mutableLiveData.setValue(results);
                }

            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {

            }
        });

        return mutableLiveData;
    }
}
