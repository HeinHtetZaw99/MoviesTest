package co.daniel.moviegasm.datasources.network.impls

import co.daniel.moviegasm.datasources.mappers.MoviesVOMapper
import co.daniel.moviegasm.datasources.network.MovieNetworkDataSource
import co.daniel.moviegasm.datasources.network.services.MoviesAPI
import co.daniel.moviegasm.domain.MoviesVO
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class MovieNetworkDataSourceImpl @Inject constructor(
    @Named("api_key") private val apiKey: String,
    private val movieApi: MoviesAPI,
    private val moviesMapper: MoviesVOMapper
) : MovieNetworkDataSource {

    private var pageNumber = 1
    private var lastPageNumber = 1

    override fun getMoviesList(): List<MoviesVO?> {

        val body = movieApi.getMoviesList(apiKey, "en-US", pageNumber).execute().body()!!
        pageNumber++
        lastPageNumber = body.totalPages
        return if(body.results == null ) arrayListOf() else  body.results.map {
                moviesMapper.map(it!!)
        } ?: arrayListOf()
    }

    override fun getTempToken(): String {
        TODO("Not yet implemented")
    }

    override fun getMovieByID(movieID: String): MoviesVO {
        TODO("Not yet implemented")
    }

    override fun getCurrentPageNumber(): Int {
        return pageNumber
    }
}