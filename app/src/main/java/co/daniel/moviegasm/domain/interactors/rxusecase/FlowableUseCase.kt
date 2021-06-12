package co.daniel.moviegasm.domain.interactors.rxusecase

import co.daniel.moviegasm.di.PostExecutionThread
import co.daniel.moviegasm.di.ThreadExecutor
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.schedulers.Schedulers

abstract class FlowableUseCase<T, Params>(
    private val postExecutionThread: PostExecutionThread, private val threadExecutor: ThreadExecutor
) {

    /**
     * Implement this method in your custom AsyncUseCase in order to provide the final [Flowable].
     *
     * @param params The Params.
     * @return The provided [Flowable].
     */
    protected abstract fun provideFlowable(params: Params): Flowable<T>

    /**
     * Builds the provided [Flowable] and performs some transformation on it.
     *
     * @return The Observable with any transformation applied.
     */
    private fun buildUseCaseFlowable(): FlowableTransformer<T, T> {

        return FlowableTransformer { flowable ->
            flowable.subscribeOn(Schedulers.from(threadExecutor)).observeOn(
                postExecutionThread.scheduler
            )
        }
    }

    /**
     * Executes the provided [Flowable]
     *
     * @param params The Params.
     * @return The provided Flowable.
     */
    fun execute(params: Params): Flowable<T> {
        val flowable = provideFlowable(params)
        return flowable.compose(buildUseCaseFlowable())
    }

}