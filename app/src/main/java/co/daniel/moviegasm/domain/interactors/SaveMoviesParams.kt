package co.daniel.moviegasm.domain.interactors

import co.daniel.moviegasm.di.PostExecutionThread
import co.daniel.moviegasm.di.ThreadExecutor
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.domain.interactors.rxusecase.CompletableUseCase
import co.daniel.moviegasm.domain.interactors.rxusecase.ObservableUseCase
import co.daniel.moviegasm.repositories.MovieRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 6/6/21.
 */
class SaveMovies @Inject constructor(
    private val movieRepository: MovieRepository,
    postExecutionThread: PostExecutionThread,
    threadExecutor: ThreadExecutor
) : CompletableUseCase< SaveMovies.SaveMoviesParams>(
    postExecutionThread,
    threadExecutor
) {

    data class SaveMoviesParams(val listToBeSaved: List<MoviesVO>)

    override fun provideCompletable(params: SaveMoviesParams): Completable {
        return movieRepository.saveMovies(params.listToBeSaved)
    }
}