package co.daniel.moviegasm

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


/**
 * Created by HeinHtetZaw on 5/19/21.
 */
@HiltAndroidApp
class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(LoggingTree())
    }

/*    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }*/

}