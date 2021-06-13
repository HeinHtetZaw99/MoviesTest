package co.daniel.moviegasm.presentation

import androidx.multidex.MultiDexApplication
import co.daniel.moviegasm.LoggingTree
import timber.log.Timber


/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class MovieApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(LoggingTree())
    }

/*    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }*/

}