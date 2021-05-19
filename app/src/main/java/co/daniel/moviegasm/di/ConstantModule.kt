package co.daniel.moviegasm.di

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object ConstantModule {

    @Provides
    @Singleton
    @JvmStatic
    @Named("api_key")
    fun providesApiKey(): String = "4d424ac53ee2e19a22f73d2d828407a1"


    @Provides
    @JvmStatic
    @Singleton
    @Named("base_url")
    fun providesBaseUrl(): String = "https://api.themoviedb.org/"

}