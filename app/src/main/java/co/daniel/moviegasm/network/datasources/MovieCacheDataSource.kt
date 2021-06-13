package co.daniel.moviegasm.network.datasources

import androidx.lifecycle.LiveData
import co.daniel.moviegasm.domain.MoviesVO
import javax.inject.Qualifier

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
interface MovieCacheDataSource {
    fun getMoviesList(pageNumber: Int): LiveData<List<MoviesVO>>
    fun getMoviesList(): LiveData<List<MoviesVO>>
    fun getTempToken(): String
    fun getMovieByID(movieID: String): LiveData<MoviesVO>
    fun saveMovies(dataList: List<MoviesVO>)
}

