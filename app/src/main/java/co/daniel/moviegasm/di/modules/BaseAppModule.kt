package co.daniel.moviegasm.di.modules


import co.daniel.moviegasm.di.GenericErrorMessageFactory
import co.daniel.moviegasm.di.JobExecutor
import co.daniel.moviegasm.di.UIThread
import co.daniel.moviegasm.network.datasources.cache.MovieCacheDataSourceImpl
import co.daniel.moviegasm.network.datasources.network.MovieNetworkDataSource
import co.daniel.moviegasm.network.datasources.network.exception.NetworkExceptionMessageFactory
import co.daniel.moviegasm.network.datasources.network.impls.MovieNetworkDataSourceImpl
import co.daniel.moviegasm.network.datasources.network.impls.helpers.GenericErrorMessageFactoryImpl
import co.daniel.moviegasm.network.datasources.network.impls.helpers.NetworkExceptionMessageFactoryImpl
import co.daniel.moviegasm.repositories.impl.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface BaseAppModule {

    @Binds
    @ThreadExecutorTag
    fun threadExecutor(jobExecutor: JobExecutor): co.daniel.moviegasm.di.ThreadExecutor

    @Binds
    @PostExecutionThreadTag
    fun postExecutionThread(uiThread: UIThread): co.daniel.moviegasm.di.PostExecutionThread

    @Binds
    @GenericErrorMessageFactoryTag
    fun genericErrorMessageFactory(genericErrorMessageFactory: GenericErrorMessageFactoryImpl): GenericErrorMessageFactory

    @Binds
    @NetworkErrorMessageFactoryTag
    fun networkErrorMessageFactory(networkExceptionMessageFactory: NetworkExceptionMessageFactoryImpl): NetworkExceptionMessageFactory

    @Binds
    @MovieRepositoryTag
    fun getMovieRepository(
        homeRepositoryImpl: MovieRepositoryImpl
    ): co.daniel.moviegasm.repositories.MovieRepository


    @Binds
    @MovieNetworkDataSourceTag
    fun homeDataSource(
        homeDataSourceImpl: MovieNetworkDataSourceImpl
    ): MovieNetworkDataSource

    @Binds
    @MovieCacheDataSourceTag
    fun movieCacheSource(
        movieCacheDataSourceImpl: MovieCacheDataSourceImpl
    ): co.daniel.moviegasm.network.datasources.MovieCacheDataSource


}