package com.supersonic.retrofitmovieapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.supersonic.retrofitmovieapp.R;
import com.supersonic.retrofitmovieapp.service.MovieService;
import com.supersonic.retrofitmovieapp.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Result> {

    private MovieService service;
    private Application application;

    public MovieDataSource(MovieService service, Application application) {
        this.service = service;
        this.application = application;
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> loadParams, @NonNull final LoadCallback<Long, Result> loadCallback) {

        service = RetrofitInstance.getService();
        Call<MovieInfo> call = service.getPopularMoviesWithPaging(application.getApplicationContext().getString(R.string.api_key),
                loadParams.key);

        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {

                MovieInfo movieInfo = response.body();
                ArrayList<Result> results = new ArrayList<>();

                if (movieInfo != null && movieInfo.getResults() != null){
                    results = (ArrayList<Result>) movieInfo.getResults();
                    loadCallback.onResult(results,loadParams.key+ 1);
                }
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> loadParams, @NonNull LoadCallback<Long, Result> loadCallback) {

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> loadInitialParams, @NonNull LoadInitialCallback<Long, Result> loadInitialCallback) {

        service = RetrofitInstance.getService();
        Call<MovieInfo> call = service.getPopularMoviesWithPaging(application.getApplicationContext().getString(R.string.api_key),
                1);

        call.enqueue(new Callback<MovieInfo>() {
            @Override
            public void onResponse(Call<MovieInfo> call, Response<MovieInfo> response) {

                MovieInfo movieInfo = response.body();
                ArrayList<Result> results = new ArrayList<>();

                if (movieInfo != null && movieInfo.getResults() != null){
                    results = (ArrayList<Result>) movieInfo.getResults();
                    loadInitialCallback.onResult(results, null, 2L);
                }
            }

            @Override
            public void onFailure(Call<MovieInfo> call, Throwable t) {

            }
        });

    }
}
