package co.daniel.moviegasm.di

import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [ServiceModule.Providers::class])
abstract class ServiceModule {

    @Module
    object Providers {

//        @JvmStatic
//        @Provides
//        @Named("UnauthRegisterService")
//        fun provideUnAuthRegisterService(retrofitBuilder : Retrofit.Builder , httpClientBuilder : OkHttpClient.Builder) : RegisterService =
//            retrofitBuilder.client(httpClientBuilder.build()).build().create(RegisterService::class.java)

//        @JvmStatic
//        @NonNull
//        @Provides
//        fun provideLoginService(@Named("authenticatedBuilder") retrofitBuilder: Retrofit.Builder): LoginService {
//            return retrofitBuilder.build().create(LoginService::class.java)
//        }

    }

}