package dev.fast25.upcomingmovie.remote

import dev.fast25.upcomingmovie.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("movie/upcoming")
    fun getUpComingMovie(): Call<Movie>


}