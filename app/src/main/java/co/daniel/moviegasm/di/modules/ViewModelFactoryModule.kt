package co.daniel.moviegasm.di.modules

import androidx.lifecycle.ViewModelProvider
import co.daniel.moviegasm.di.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun viewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}