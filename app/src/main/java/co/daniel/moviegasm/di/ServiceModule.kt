package co.daniel.moviegasm.di

import co.daniel.moviegasm.datasources.network.services.MoviesAPI
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

//        @JvmStatic
//        @NonNull
//        @Provides
//        fun provideLoginService(@Named("authenticatedBuilder") retrofitBuilder: Retrofit.Builder): LoginService {
//            return retrofitBuilder.build().create(LoginService::class.java)
//        }

    }

}