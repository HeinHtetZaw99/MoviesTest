package co.daniel.moviegasm.datasources

import androidx.lifecycle.LiveData
import co.daniel.moviegasm.domain.MoviesVO

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
interface MovieCacheDataSource {
    fun getMoviesList(pageNumber: Int): LiveData<List<MoviesVO>>
    fun getTempToken(): String
    fun getMovieByID(movieID: String): LiveData<MoviesVO>
    fun saveMovies(dataList: List<MoviesVO>)
}