package co.daniel.moviegasm.di.modules

import co.daniel.moviegasm.activities.details.MovieDetailsActivity
import co.daniel.moviegasm.activities.home.HomeScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun homeScreenActivity() : HomeScreenActivity

    @ContributesAndroidInjector
    abstract fun movieDetailsActivity() : MovieDetailsActivity

}