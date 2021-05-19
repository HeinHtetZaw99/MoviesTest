package co.daniel.moviegasm.di

import android.content.Context
import co.daniel.moviegasm.BuildConfig
import co.daniel.moviegasm.datasources.cache.SharePrefUtils
import co.daniel.moviegasm.datasources.network.MovieNetworkDataSource
import co.daniel.moviegasm.datasources.network.dummy.FakeMovieNetworkDataSourceImpl
import co.daniel.moviegasm.datasources.network.exception.NetworkExceptionInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [NetworkModule.Providers::class, ServiceModule.Providers::class, ConstantModule::class])
abstract class NetworkModule {


    @Binds
    abstract fun homeDataSource(
        homeDataSourceImpl: FakeMovieNetworkDataSourceImpl
    ): MovieNetworkDataSource


    @Module
    object Providers {
        @JvmStatic
        @Provides
        fun providesOkHttpClientBuilder(context: Context): OkHttpClient.Builder {

            return OkHttpClient.Builder().apply {
                val loggerInterceptor = HttpLoggingInterceptor().apply {
                    level = when (BuildConfig.DEBUG) {
                        true -> HttpLoggingInterceptor.Level.HEADERS
                        false -> HttpLoggingInterceptor.Level.NONE
                    }
                }

                addInterceptor(loggerInterceptor)
                    .addInterceptor(ChuckInterceptor(context))
                    .addInterceptor(NetworkExceptionInterceptor())

                    .readTimeout(8, TimeUnit.SECONDS)
                    .writeTimeout(8, TimeUnit.SECONDS)
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .cache(null)
            }
        }

//        @JvmStatic
//        @Provides
//        @Singleton
//        @Named("client_id")
//        fun providesClientID() = "1"

//        @JvmStatic
//        @Provides
//        @Singleton
//        @Named("token")
//        fun providesToken() = "e721305f223ce88030cabcaaad8a827c5ed3e6b29dd9d58538baea15dee74b00"


        @JvmStatic
        @Provides
        @Singleton
        @Named("api_key")
        fun providesApiKey() = "test-api-key"

        @Provides
        @JvmStatic
        @Singleton
        fun providesSharePrefUtils(context: Context) =
            SharePrefUtils(context)

        @JvmStatic
        @Provides
        @Named("bearer_token")
        fun providesBearerToken(sharePrefUtils: SharePrefUtils) =
            sharePrefUtils.load(SharePrefUtils.KEYS.ACCESS_TOKEN)

        @JvmStatic
        @Provides
        @Named("bearer_token_bearer")
        fun providesBearerBToken(sharePrefUtils: SharePrefUtils) =
            "Bearer " + sharePrefUtils.load(SharePrefUtils.KEYS.ACCESS_TOKEN)

        @Suppress("DEPRECATION")
        @JvmStatic
        @Provides
        @Named("primary")
        fun providesPrimaryRetrofitBuilder(
            gson: Gson,
            @Named("base_url") baseUrl: String
        ): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        }


        @JvmStatic
        @Provides
        @Singleton
        fun gson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .create()


    }
}