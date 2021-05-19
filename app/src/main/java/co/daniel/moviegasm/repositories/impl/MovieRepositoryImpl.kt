package co.daniel.moviegasm.repositories.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import co.daniel.moviegasm.datasources.MovieCacheDataSource
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
    private val movieNetworkDataSource: MovieNetworkDataSource,
    private val movieCacheDataSource: MovieCacheDataSource
) : MovieRepository {

    private val mediator = MediatorLiveData<List<MoviesVO>>()
    private var cachePageNumber = 1

    override fun getMoviesList(): Observable<List<MoviesVO?>> {
        return Observable.fromCallable { movieNetworkDataSource.getMoviesList() }
    }

    override fun getMoviesAPIKey(): Observable<String> {
        return Observable.fromCallable { movieNetworkDataSource.getTempToken() }
    }

    override fun saveMovieAPIKEY(key: String): Completable {
        return Completable.complete()
    }

    override fun getMovieByID(movieID: String): LiveData<MoviesVO> {
        return movieCacheDataSource.getMovieByID(movieID)
    }

    override fun getCachedMovies(fetchFromStart: Boolean): LiveData<List<MoviesVO>> {
        return movieCacheDataSource.getMoviesList(if (fetchFromStart) 1 else cachePageNumber).also{
            if(fetchFromStart)
                cachePageNumber = 1
            else
                cachePageNumber++
        }
    }

    override fun saveMovies(dataList: List<MoviesVO>) {
        movieCacheDataSource.saveMovies(dataList)
    }

    override fun getMoviesListSource(): LiveData<List<MoviesVO>> {

        return mediator
    }
}