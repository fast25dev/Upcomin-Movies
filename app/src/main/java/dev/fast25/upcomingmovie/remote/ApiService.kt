package dev.fast25.upcomingmovie.remote

import dev.fast25.upcomingmovie.model.DetailResponce
import dev.fast25.upcomingmovie.model.ImageResponse
import dev.fast25.upcomingmovie.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getUpComingMovie(
        @Query("page") page: Int = 1
    ): Call<Movie>

    @GET("search/movie")
    fun searchMovieByName(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Call<Movie>

    @GET("movie/{id}")
    fun getMovieById(
        @Path("id") id: Int,
    ): Call<DetailResponce>


    @GET("movie/{id}/images")
    fun getImagesById(
        @Path("id") id: Int,
    ): Call<ImageResponse>

}