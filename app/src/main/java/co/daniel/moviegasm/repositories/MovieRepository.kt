package co.daniel.moviegasm.repositories

import androidx.lifecycle.LiveData
import co.daniel.moviegasm.domain.MoviesVO
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
interface MovieRepository {
    fun getMoviesList(fetchFromStart : Boolean) : Observable<List<MoviesVO>>

    fun saveMovieAPIKEY( key : String ) : Completable
    fun getMovieByID(movieID: String): LiveData<MoviesVO>

    fun getCachedMovies() : LiveData<List<MoviesVO>>
    fun saveMovies(dataList : List<MoviesVO>) : Completable

}