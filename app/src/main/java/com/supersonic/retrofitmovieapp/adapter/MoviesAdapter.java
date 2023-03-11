package com.supersonic.retrofitmovieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.supersonic.retrofitmovieapp.R;
import com.supersonic.retrofitmovieapp.databinding.RecyclerViewItemBinding;
import com.supersonic.retrofitmovieapp.model.Result;
import com.supersonic.retrofitmovieapp.view.MovieActivity;

public class MoviesAdapter extends PagedListAdapter<Result,MoviesAdapter.MovieViewHolder> {

    Context context;

    public MoviesAdapter(Context context) {
        super(Result.CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerViewItemBinding recyclerViewItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.recycler_view_item, parent, false);

        return new MovieViewHolder(recyclerViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Result result = getItem(position);
        holder.recyclerViewItemBinding.setResult(result);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewItemBinding recyclerViewItemBinding;

        public MovieViewHolder(@NonNull RecyclerViewItemBinding recyclerViewItemBinding) {
            super(recyclerViewItemBinding.getRoot());
            this.recyclerViewItemBinding = recyclerViewItemBinding;


            recyclerViewItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {

                        Result result = getItem(position);
                        Intent intent = new Intent(context, MovieActivity.class);
                        intent.putExtra("result", result);
                        context.startActivity(intent);

                    }
                }
            });
        }
    }
}
