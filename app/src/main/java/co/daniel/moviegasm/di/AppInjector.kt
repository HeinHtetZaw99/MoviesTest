package co.daniel.moviegasm.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {

    fun initAutoInjection(application: Application) {

        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity) {}

            override fun onActivityResumed(p0: Activity) {}

            override fun onActivityStarted(p0: Activity) {}

            override fun onActivityDestroyed(p0: Activity) {}

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}

            override fun onActivityStopped(p0: Activity) {}

            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                handleActivity(activity)
            }

        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }

                        }
                    }, true
                )
        }
    }
}
