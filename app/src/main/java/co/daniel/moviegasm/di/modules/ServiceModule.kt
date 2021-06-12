package co.daniel.moviegasm.di.modules

import co.daniel.moviegasm.network.datasources.network.services.MoviesAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module(includes = [ServiceModule.Providers::class])
abstract class ServiceModule {

    @Module
    object Providers {

        @JvmStatic
        @Provides
        fun provideMoviesAPI(
            retrofitBuilder: Retrofit.Builder,
            httpClientBuilder: OkHttpClient.Builder
        ): MoviesAPI =
            retrofitBuilder.client(httpClientBuilder.build()).build().create(MoviesAPI::class.java)

    }

}