package co.daniel.moviegasm.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import co.daniel.moviegasm.di.GenericErrorMessageFactory
import co.daniel.moviegasm.di.modules.GenericErrorMessageFactoryTag
import co.daniel.moviegasm.viewmodel.BaseViewModel
import javax.inject.Inject


abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    @Inject
    @GenericErrorMessageFactoryTag
    lateinit var genericErrorMessageFactory: GenericErrorMessageFactory

    protected abstract val viewModel: VM

    var currentOrientation = Configuration.ORIENTATION_PORTRAIT

    abstract fun loadData()

    abstract fun initUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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