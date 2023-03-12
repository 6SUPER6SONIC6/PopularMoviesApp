package com.supersonic.retrofitmovieapp.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.supersonic.retrofitmovieapp.R;
import com.supersonic.retrofitmovieapp.adapter.MoviesAdapter;
import com.supersonic.retrofitmovieapp.databinding.ActivityMainBinding;
import com.supersonic.retrofitmovieapp.model.Result;
import com.supersonic.retrofitmovieapp.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String POPULAR = "Popular movies";
    private static final String TOP_RATED = "Top Rated movies";

    private PagedList<Result> resultArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
//    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;

    String[] items = {POPULAR, TOP_RATED};
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

//        mainActivityViewModel = new ViewModelProvider
//                .AndroidViewModelFactory(getApplication())
//                .create(MainActivityViewModel.class);



        Spinner spinner = activityMainBinding.spinner;

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();

                getMovies(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        swipeRefreshLayout = activityMainBinding.swiperefresh;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovies(selectedItem);
            }
        });
    }

    private void getMovies(String selectedItem) {

        MainActivityViewModel mainActivityViewModel = new MainActivityViewModel(getApplication(), selectedItem);

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