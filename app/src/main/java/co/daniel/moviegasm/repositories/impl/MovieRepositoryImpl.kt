package co.daniel.moviegasm.repositories.impl

import androidx.lifecycle.LiveData
import co.daniel.moviegasm.di.PostExecutionThread
import co.daniel.moviegasm.di.ThreadExecutor
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.network.datasources.MovieCacheDataSource
import co.daniel.moviegasm.network.datasources.network.MovieNetworkDataSource
import co.daniel.moviegasm.repositories.MovieRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class MovieRepositoryImpl @Inject constructor(
    private val movieNetworkDataSource: MovieNetworkDataSource,
    private val movieCacheDataSource: MovieCacheDataSource,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) : MovieRepository {

    val moviesListItems = BehaviorSubject.create<List<MoviesVO>>()
    private val compositeDisposable = CompositeDisposable()

    private var cachePageNumber = 1

    override fun getMoviesList(fetchFromStart : Boolean): Observable<List<MoviesVO>> {

        return Observable.fromCallable { movieNetworkDataSource.getMoviesList(fetchFromStart).filterNotNull() }
        /* Observable.fromCallable { movieNetworkDataSource.getMoviesList().filterNotNull() }
             .subscribeOn(Schedulers.from(threadExecutor)).observeOn(
                 postExecutionThread.scheduler
             ).subscribe({
                 moviesListItems.onNext(it)
             }, {
                 getCachedMovies()
             }).addTo(compositeDisposable)

         return moviesListItems*/
    }

    override fun saveMovieAPIKEY(key: String): Completable {
        return Completable.complete()
    }

    override fun getMovieByID(movieID: String): LiveData<MoviesVO> {
        return movieCacheDataSource.getMovieByID(movieID)
    }

    override fun getCachedMovies(): LiveData<List<MoviesVO>> {
        /*return movieCacheDataSource.getMoviesList(if (fetchFromStart) 1 else cachePageNumber).also {
            if (fetchFromStart)
                cachePageNumber = 1
            else
                cachePageNumber++
        }*/
        return movieCacheDataSource.getMoviesList()
    }

    override fun saveMovies(dataList: List<MoviesVO>): Completable {
        return Completable.fromAction { movieCacheDataSource.saveMovies(dataList) }
    }

}