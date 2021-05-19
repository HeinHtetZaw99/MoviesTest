package co.daniel.moviegasm.repositories

import co.daniel.moviegasm.domain.MoviesVO
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
interface MovieRepository {
    fun getMoviesList(pageNumber : Int) : Observable<List<MoviesVO>>
    fun getMoviesAPIKey() : Observable<String>
    fun saveMovieAPIKEY( key : String ) : Completable
    fun getMovieByID(movieID: String): Observable<MoviesVO>
}