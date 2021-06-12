package co.daniel.moviegasm.di.modules

import co.daniel.moviegasm.presentation.activities.details.MovieDetailsActivity
import co.daniel.moviegasm.presentation.activities.home.HomeScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun homeScreenActivity() : HomeScreenActivity

    @ContributesAndroidInjector
    abstract fun movieDetailsActivity() : MovieDetailsActivity

}