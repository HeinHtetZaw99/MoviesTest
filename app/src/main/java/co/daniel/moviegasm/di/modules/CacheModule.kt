package co.daniel.moviegasm.di.modules


import co.daniel.moviegasm.network.datasources.MovieCacheDataSource
import co.daniel.moviegasm.network.datasources.cache.MovieCacheDataSourceImpl
import co.daniel.moviegasm.network.datasources.cache.room.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [CacheModule.Provider::class])
abstract class CacheModule {
    @Binds
    abstract fun movieCacheSource(
        movieCacheDataSourceImpl: MovieCacheDataSourceImpl
    ): MovieCacheDataSource


    @Module
    object Provider {
        @Provides
        @JvmStatic
        @Singleton
        fun getOTPDao(appDatabase: AppDatabase) = appDatabase.getMovieDao()
    }

}
