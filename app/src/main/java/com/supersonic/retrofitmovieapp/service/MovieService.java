package com.supersonic.retrofitmovieapp.service;

import com.supersonic.retrofitmovieapp.model.MovieInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Call<MovieInfo> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieInfo> getPopularMoviesWithPaging(@Query("api_key") String apiKey, @Query("page") long page);
}
