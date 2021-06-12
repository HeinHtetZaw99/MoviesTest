package co.daniel.moviegasm.network.datasources.network.services

import co.daniel.moviegasm.network.datasources.network.responses.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MoviesAPI {
    @GET("/3/movie/top_rated")
    fun getMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Call<MovieListResponse>

}