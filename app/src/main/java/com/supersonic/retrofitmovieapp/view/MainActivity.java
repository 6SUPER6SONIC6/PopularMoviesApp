package com.supersonic.retrofitmovieapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;

import com.supersonic.retrofitmovieapp.adapter.MoviesAdapter;
import com.supersonic.retrofitmovieapp.R;
import com.supersonic.retrofitmovieapp.databinding.ActivityMainBinding;
import com.supersonic.retrofitmovieapp.model.MovieInfo;
import com.supersonic.retrofitmovieapp.model.Result;
import com.supersonic.retrofitmovieapp.service.MovieService;
import com.supersonic.retrofitmovieapp.service.RetrofitInstance;
import com.supersonic.retrofitmovieapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private PagedList<Result> resultArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);

        getPopularMovies();

        swipeRefreshLayout = activityMainBinding.swiperefresh;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovies();
            }
        });
    }

    private void getPopularMovies() {

        mainActivityViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(PagedList<Result> results) {
                resultArrayList = results;
                inflateRecyclerView();
            }
        });

    }

    private void inflateRecyclerView(){

        RecyclerView recyclerView = activityMainBinding.recyclerView;
        MoviesAdapter moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.submitList(resultArrayList);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

}