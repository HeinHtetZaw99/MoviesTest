package co.daniel.moviegasm.repositories.impl

import co.daniel.moviegasm.datasources.network.MovieNetworkDataSource
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.repositories.MovieRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieNetworkDataSource: MovieNetworkDataSource
) : MovieRepository {
    override fun getMoviesList(pageNumber: Int): Observable<List<MoviesVO>> {
        return Observable.fromCallable { movieNetworkDataSource.getMoviesList(pageNumber) }
    }

    override fun getMoviesAPIKey(): Observable<String> {
        return Observable.fromCallable { movieNetworkDataSource.getTempToken() }
    }

    override fun saveMovieAPIKEY(key: String): Completable {
        return Completable.complete()
    }

    override fun getMovieByID(movieID: String): Observable<MoviesVO> {
        return Observable.fromCallable { movieNetworkDataSource.getMovieByID(movieID) }
    }
}