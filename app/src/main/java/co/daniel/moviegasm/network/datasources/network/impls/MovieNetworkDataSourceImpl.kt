package co.daniel.moviegasm.network.datasources.network.impls

import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.network.datasources.mappers.MoviesVOMapper
import co.daniel.moviegasm.network.datasources.network.MovieNetworkDataSource
import co.daniel.moviegasm.network.datasources.network.services.MoviesAPI
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

    override fun getMoviesList(fetchFromStart: Boolean): List<MoviesVO?> {
        val body =
            movieApi.getMoviesList(apiKey, "en-US", if (fetchFromStart) 1 else pageNumber).execute()
                .body()!!
        if (fetchFromStart) {
            pageNumber = 1
        } else {
            pageNumber++
        }

        lastPageNumber = body.totalPages
        return if (body.results == null) arrayListOf() else body.results.map {
            moviesMapper.map(it!!, pageNumber - 1)
        }
    }


    override fun getCurrentPageNumber(): Int {
        return pageNumber
    }
}