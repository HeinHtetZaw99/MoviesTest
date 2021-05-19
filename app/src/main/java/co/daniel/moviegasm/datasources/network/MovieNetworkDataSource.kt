package co.daniel.moviegasm.datasources.network

import co.daniel.moviegasm.domain.MoviesVO

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
interface MovieNetworkDataSource {
    fun getMoviesList(pageNumber : Int ): List<MoviesVO>
    fun getTempToken() : String
    fun getMovieByID(movieID: String) : MoviesVO
}