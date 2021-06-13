package co.daniel.moviegasm.di.modules

import co.daniel.moviegasm.repositories.impl.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [CacheModule::class, NetworkModule::class])
interface RepositoryModule {



}