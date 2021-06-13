package co.daniel.moviegasm.di.modules

import javax.inject.Qualifier

/**
 * Created by HeinHtetZaw on 12/06/2021.
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieCacheDataSourceTag

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieNetworkDataSourceTag

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieRepositoryTag

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ThreadExecutorTag

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PostExecutionThreadTag

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GenericErrorMessageFactoryTag

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class NetworkErrorMessageFactoryTag