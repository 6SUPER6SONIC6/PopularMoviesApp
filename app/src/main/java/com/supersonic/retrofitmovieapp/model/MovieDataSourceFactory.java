package com.supersonic.retrofitmovieapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.supersonic.retrofitmovieapp.service.MovieService;

public class MovieDataSourceFactory extends DataSource.Factory {

    private Application application;
    private MovieDataSource movieDataSource;
    private MovieService movieService;
    private MutableLiveData<MovieDataSource> mutableLiveData;
    private String selectedItem;


    public MovieDataSourceFactory(Application application, MovieService movieService, String selectedItem) {
        this.application = application;
        this.movieService = movieService;
        this.selectedItem = selectedItem;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {

        movieDataSource = new MovieDataSource(movieService, application, selectedItem);
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
