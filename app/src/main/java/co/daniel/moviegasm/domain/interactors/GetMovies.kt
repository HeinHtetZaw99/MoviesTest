package co.daniel.moviegasm.domain.interactors

import co.daniel.moviegasm.di.PostExecutionThread
import co.daniel.moviegasm.di.ThreadExecutor
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.domain.interactors.rxusecase.ObservableUseCase
import co.daniel.moviegasm.repositories.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 6/6/21.
 */
class GetMovies @Inject constructor(
    private val movieRepository: MovieRepository,
    postExecutionThread: PostExecutionThread,
    threadExecutor: ThreadExecutor
) : ObservableUseCase<List<MoviesVO>, GetMovies.GetMoviesParams>(
    postExecutionThread,
    threadExecutor
) {

    data class GetMoviesParams(val isFromCache : Boolean = false,val fetchFromStart : Boolean = false )

    override fun provideObservable(params: GetMoviesParams): Observable<List<MoviesVO>> {
        return movieRepository.getMoviesList(params.fetchFromStart)
    }
}