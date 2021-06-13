package co.daniel.moviegasm.di.modules

import co.daniel.moviegasm.network.datasources.network.services.MoviesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMoviesAPI(
        retrofitBuilder: Retrofit.Builder,
        httpClientBuilder: OkHttpClient.Builder
    ): MoviesAPI =
        retrofitBuilder.client(httpClientBuilder.build()).build().create(MoviesAPI::class.java)

}