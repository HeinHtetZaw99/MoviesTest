package co.daniel.moviegasm.domain.interactors.rxusecase


import co.daniel.moviegasm.di.PostExecutionThread
import co.daniel.moviegasm.di.ThreadExecutor
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<Params>(
    private val postExecutionThread: PostExecutionThread, private val threadExecutor: ThreadExecutor
) {

    /**
     * Implement this method in your custom AsyncUseCase in order to provide the final [Completable].
     *
     * @param params The Params.
     * @return The provided [Completable].
     */
    protected abstract fun provideCompletable(params: Params): Completable

    /**
     * Builds the provided [Completable] and performs some transformation on it.
     *
     * @return The Observable with any transformation applied.
     */
    private fun buildUseCaseCompletable(): CompletableTransformer {

        return CompletableTransformer { Completable ->
            Completable.subscribeOn(Schedulers.from(threadExecutor)).observeOn(
                postExecutionThread.scheduler
            )
        }
    }

    /**
     * Executes the provided [Completable]
     *
     * @param params The Params.
     * @return The provided Completable.
     */
    open fun execute(params: Params): Completable {
        val completable = provideCompletable(params)
        return completable.compose(buildUseCaseCompletable())
    }

}