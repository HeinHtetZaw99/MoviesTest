package co.daniel.moviegasm.di.modules


import co.daniel.moviegasm.network.datasources.cache.room.AppDatabase
import co.daniel.moviegasm.network.datasources.cache.room.daos.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.getMovieDao()
    }
}



