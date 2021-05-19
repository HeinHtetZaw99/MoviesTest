package co.daniel.moviegasm.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.daniel.moviegasm.di.GenericErrorMessageFactory
import co.daniel.moviegasm.viewmodel.BaseViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import kotlin.reflect.KClass


abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity(),
    HasAndroidInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var genericErrorMessageFactory: GenericErrorMessageFactory

    protected abstract val viewModel: VM

    var currentOrientation = Configuration.ORIENTATION_PORTRAIT

    abstract fun loadData()

    abstract fun initUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    /**
     * Helper function for easily init of viewModel
     */
    inline fun <reified VM : BaseViewModel> contractedViewModels(): Lazy<VM> =
        ViewModelLazy(VM::class)


    inner class ViewModelLazy<VM : ViewModel>(
        private val viewModelClass: KClass<VM>
    ) : Lazy<VM> {
        private var cached: VM? = null

        override val value: VM
            get() {
                var viewModel = cached
                if (viewModel == null) {
                    viewModel = ViewModelProvider(
                        this@BaseActivity,
                        viewModelFactory
                    ).get(viewModelClass.java)
                    cached = viewModel
                }
                return viewModel
            }

        override fun isInitialized() = cached != null
    }

    fun createView(@LayoutRes layout: Int) = LayoutInflater.from(this).inflate(layout, null)!!

    fun goToAppSettings() {
        startActivity(
            Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null)
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        // ignore orientation change
        if (newConfig.orientation != currentOrientation) {

            super.onConfigurationChanged(newConfig)

        }
        currentOrientation = newConfig.orientation
        Log.i("Orientation", "current :: $currentOrientation")
    }

}