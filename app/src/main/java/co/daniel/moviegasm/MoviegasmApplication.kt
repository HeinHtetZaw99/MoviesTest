package co.daniel.moviegasm

import android.app.Application
import co.daniel.moviegasm.di.AppComponent
import co.daniel.moviegasm.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class MoviegasmApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private var applicationComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        Timber.plant(LoggingTree())


        if (applicationComponent == null) {
            applicationComponent = reinitApplicationComponent()
        }

    }

    //for recreating dagger instances
    fun reinitApplicationComponent(): AppComponent? {
        applicationComponent = null
        applicationComponent = DaggerAppComponent.builder().application(this).build()
        applicationComponent!!.inject(this)
        return applicationComponent
    }


    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}