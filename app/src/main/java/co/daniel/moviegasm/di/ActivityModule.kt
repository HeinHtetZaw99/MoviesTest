package co.daniel.moviegasm.di

import co.daniel.moviegasm.activities.HomeScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun homeScreenActivity() : HomeScreenActivity

}