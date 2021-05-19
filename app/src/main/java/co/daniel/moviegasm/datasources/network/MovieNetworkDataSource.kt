package co.daniel.moviegasm.datasources.network

import co.daniel.moviegasm.datasources.network.responses.MovieListResponse
import co.daniel.moviegasm.domain.MoviesVO

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
interface MovieNetworkDataSource {
    fun getMoviesList():  List<MoviesVO?>
    fun getTempToken() : String
    fun getMovieByID(movieID: String) : MoviesVO
    fun getCurrentPageNumber() : Int
}