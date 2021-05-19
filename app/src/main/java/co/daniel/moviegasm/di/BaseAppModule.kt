package co.daniel.moviegasm.di

import android.app.Application
import android.content.Context
import co.daniel.moviegasm.datasources.network.exception.NetworkExceptionMessageFactory
import co.daniel.moviegasm.datasources.network.GenericErrorMessageFactoryImpl
import com.appbase.exception.NetworkExceptionMessageFactoryImpl

import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [BaseAppModule.Provider::class, ViewModelFactoryModule::class, RepositoryModule::class])
abstract class BaseAppModule {

    @Binds
    abstract fun threadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun postExecutionThread(uiThread: UIThread): PostExecutionThread

    @Binds
    abstract fun genericErrorMessageFactory(genericErrorMessageFactory: GenericErrorMessageFactoryImpl): GenericErrorMessageFactory

    @Binds
    abstract fun networkErrorMessageFactory(networkExceptionMessageFactory: NetworkExceptionMessageFactoryImpl): NetworkExceptionMessageFactory

    @Module
    object Provider {
        @Provides
        @JvmStatic
        @Singleton
        fun providesContext(application: Application): Context = application.applicationContext!!

    }

}