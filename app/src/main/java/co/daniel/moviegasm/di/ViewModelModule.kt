package co.daniel.moviegasm.di

import androidx.lifecycle.ViewModel
import co.daniel.moviegasm.viewmodel.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(
        homeScreenViewModel: HomeViewModel
    ): ViewModel


}