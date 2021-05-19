package co.daniel.moviegasm.di

import dagger.Module

@Module(includes = [CacheModule::class, NetworkModule::class])
abstract class RepositoryModule {
//
//    @Binds
//    abstract fun getHomeRepository(
//        homeRepositoryImpl: HomeRepositoryImpl
//    ): HomeRepository

}