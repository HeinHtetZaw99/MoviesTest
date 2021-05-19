package co.daniel.moviegasm.di.modules


import co.daniel.moviegasm.datasources.MovieCacheDataSource
import co.daniel.moviegasm.datasources.cache.MovieCacheDataSourceImpl
import co.daniel.moviegasm.datasources.cache.room.AppDatabase
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
