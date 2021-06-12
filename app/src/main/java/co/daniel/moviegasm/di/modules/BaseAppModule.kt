package co.daniel.moviegasm.di.modules

import android.app.Application
import android.content.Context
import co.daniel.moviegasm.network.datasources.network.exception.NetworkExceptionMessageFactory
import co.daniel.moviegasm.network.datasources.network.impls.helpers.GenericErrorMessageFactoryImpl
import co.daniel.moviegasm.network.datasources.network.impls.helpers.NetworkExceptionMessageFactoryImpl
import co.daniel.moviegasm.di.*

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