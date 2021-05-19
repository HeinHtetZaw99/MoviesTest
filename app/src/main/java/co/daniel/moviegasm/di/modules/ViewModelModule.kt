package co.daniel.moviegasm.di.modules

import androidx.lifecycle.ViewModel
import co.daniel.moviegasm.di.ViewModelKey
import co.daniel.moviegasm.viewmodel.HomeViewModel
import co.daniel.moviegasm.viewmodel.MovieDetailsViewModel
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


    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun movieDetailsViewModel(
        movieDetailsViewModel: MovieDetailsViewModel
    ): ViewModel

}