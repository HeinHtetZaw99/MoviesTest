package co.daniel.moviegasm.di.modules

import android.content.Context
import co.daniel.moviegasm.BuildConfig
import co.daniel.moviegasm.network.datasources.cache.SharePrefUtils

import co.daniel.moviegasm.network.datasources.network.exception.NetworkExceptionInterceptor
import co.daniel.moviegasm.network.datasources.network.impls.MovieNetworkDataSourceImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module(includes = [ConstantModule::class])
object NetworkModule {


    @Provides
    fun providesOkHttpClientBuilder(@ApplicationContext context: Context): OkHttpClient.Builder {

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


    @Provides
    @Singleton
    fun providesSharePrefUtils(@ApplicationContext context: Context) =
        SharePrefUtils(context)

    @Provides
    fun providesPrimaryRetrofitBuilder(
        gson: Gson,
        @Named("base_url") baseUrl: String
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }


    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setLenient()
        .create()

}