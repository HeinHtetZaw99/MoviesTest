package co.daniel.moviegasm.datasources.network

import co.daniel.moviegasm.domain.MoviesVO

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
interface MovieNetworkDataSource {
    fun getMoviesList(): List<MoviesVO?>
    fun getCurrentPageNumber(): Int
}