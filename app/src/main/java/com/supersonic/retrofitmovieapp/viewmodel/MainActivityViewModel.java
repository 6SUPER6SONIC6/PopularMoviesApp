package com.supersonic.retrofitmovieapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.supersonic.retrofitmovieapp.model.MovieDataSource;
import com.supersonic.retrofitmovieapp.model.MovieDataSourceFactory;
import com.supersonic.retrofitmovieapp.model.MovieRepository;
import com.supersonic.retrofitmovieapp.model.Result;
import com.supersonic.retrofitmovieapp.service.MovieService;
import com.supersonic.retrofitmovieapp.service.RetrofitInstance;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<MovieDataSource> movieDataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<Result>> pagedListLiveData;
    MovieDataSourceFactory movieDataSourceFactory;


    public MainActivityViewModel(@NonNull Application application, String selectedItem) {
        super(application);
        movieRepository = new MovieRepository(application);

        MovieService movieService = RetrofitInstance.getService();
        MovieDataSourceFactory movieDataSourceFactory =
                new MovieDataSourceFactory(application, movieService, selectedItem);

        movieDataSourceLiveData = movieDataSourceFactory.getMutableLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)
                .setPrefetchDistance(3)
                .build();

        executor = Executors.newCachedThreadPool();

        pagedListLiveData = new LivePagedListBuilder<Long, Result>(movieDataSourceFactory, config)
                .setFetchExecutor(executor)
                .build();

    }

    public LiveData<List<Result>> getAllMovieData(){


        return movieRepository.getMutableLiveData();
    }

    public LiveData<PagedList<Result>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
