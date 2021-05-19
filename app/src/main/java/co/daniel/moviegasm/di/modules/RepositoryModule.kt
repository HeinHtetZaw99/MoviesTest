package co.daniel.moviegasm.di.modules

import co.daniel.moviegasm.repositories.MovieRepository
import co.daniel.moviegasm.repositories.impl.MovieRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(includes = [CacheModule::class, NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun getMovieRepository(
        homeRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

}