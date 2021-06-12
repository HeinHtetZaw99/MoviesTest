package co.daniel.moviegasm.network.datasources.network

import co.daniel.moviegasm.domain.MoviesVO

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
interface MovieNetworkDataSource {
    fun getMoviesList(fetchFromStart: Boolean): List<MoviesVO?>
    fun getCurrentPageNumber(): Int
}