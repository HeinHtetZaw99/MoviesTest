package co.daniel.moviegasm.domain.interactors

import co.daniel.moviegasm.di.PostExecutionThread
import co.daniel.moviegasm.di.ThreadExecutor
import co.daniel.moviegasm.di.modules.MovieRepositoryTag
import co.daniel.moviegasm.di.modules.PostExecutionThreadTag
import co.daniel.moviegasm.di.modules.ThreadExecutorTag
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.domain.interactors.rxusecase.CompletableUseCase
import co.daniel.moviegasm.repositories.MovieRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 6/6/21.
 */
class SaveMovies @Inject constructor(
    @MovieRepositoryTag private val movieRepository: MovieRepository,
    @PostExecutionThreadTag postExecutionThread: PostExecutionThread,
    @ThreadExecutorTag threadExecutor: ThreadExecutor
) : CompletableUseCase<SaveMovies.SaveMoviesParams>(
    postExecutionThread,
    threadExecutor
) {

    data class SaveMoviesParams(val listToBeSaved: List<MoviesVO>)

    override fun provideCompletable(params: SaveMoviesParams): Completable {
        return movieRepository.saveMovies(params.listToBeSaved)
    }
}