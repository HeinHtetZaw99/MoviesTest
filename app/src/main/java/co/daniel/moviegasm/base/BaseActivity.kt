package co.daniel.moviegasm.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Insets
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.daniel.moviegasm.R
import co.daniel.moviegasm.di.GenericErrorMessageFactory
import co.daniel.moviegasm.domain.ReturnResult
import co.daniel.moviegasm.viewmodel.BaseViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.internal.functions.Functions.emptyConsumer
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import java.io.IOException
import java.net.SocketException
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


    private var displayMetrics: DisplayMetrics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    /**For Showing snackbar with action btn
     * @param view -> parent view where the snackbar will be displayed
     * @param returnResult -> error data with status
     */
    fun showSnackBar(view: View, returnResult: ReturnResult<*>) {
        val snackBar: Snackbar
        when (returnResult) {
            is ReturnResult.ErrorResult -> {
                snackBar = Snackbar.make(
                    view,
                    returnResult.getContent(),
                    BaseTransientBottomBar.LENGTH_LONG
                )
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.error
                    )
                )
            }
            is ReturnResult.NetworkErrorResult -> {
                snackBar = Snackbar.make(
                    view,
                    view.context.getString(R.string.error_msg_no_network),
                    BaseTransientBottomBar.LENGTH_LONG
                )
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.error
                    )
                )

            }

            is ReturnResult.PositiveResult -> {
                snackBar = Snackbar.make(
                    view,
                    returnResult.getContent(),
                    BaseTransientBottomBar.LENGTH_LONG
                )
                snackBar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.colorPrimary
                    )
                )
            }
            else -> {
                //do nothing
                Timber.e("There's nothing to show with snackbar")
                return
            }
        }
//        snackBar.setAction(view.context.getText(R.string.btn_msg_ok)) {
//            snackBar.dismiss()
//        }
//        snackBar.setActionTextColor(ContextCompat.getColor(view.context , R.color.white))
        snackBar.show()
    }

    fun showErrorSnackbar(view: View, errorMsg: String) {
        val snackBar = Snackbar.make(
            view,
            errorMsg,
            BaseTransientBottomBar.LENGTH_LONG
        )
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                R.color.colorPrimary
            )
        )
        snackBar.setAction(view.context.getText(R.string.btn_msg_ok)) {
            snackBar.dismiss()
        }
        snackBar.setActionTextColor(ContextCompat.getColor(view.context, R.color.white))
        snackBar.show()
    }

    /**method for getting the current screen's height with desired percentage */
    fun getScreenHeight(percentage: Double = 1.0): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            ((windowMetrics.bounds.height() - insets.top - insets.bottom) * percentage).toInt()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            (displayMetrics.heightPixels * percentage).toInt()
        }
    }


    /**method for getting the current screen's width with desired percentage */
    fun getScreenWidth(percentage: Double = 1.0): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            ((windowMetrics.bounds.width() - insets.left - insets.right) * percentage).toInt()
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            (displayMetrics.widthPixels * percentage).toInt()
        }
    }

    fun changeStatusColor(@ColorRes color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
    }

    /**Method for creating dialogs with custom views
     * @param view -> view inflated with desired layout
     * @param gravity -> gravity of the dialog to be displayed
     * @param cancelable -> whether the dialog is cancelable or not by touching outside of the dialog
     */
    fun createCustomDialog(
        context: Context?,
        view: View?,
        gravity: Int,
        cancelable: Boolean
    ): AlertDialog {
        val dialog = AlertDialog.Builder(context).create()
        dialog.setView(view)
        val window = dialog.window
        window?.setGravity(gravity)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        val layoutParams = window.attributes
        layoutParams.y = 40 // bottom margin
        window.attributes = layoutParams
        dialog.setCancelable(cancelable)
        return dialog
    }

    /**Method for modifying and overriding alertDialogs width and height as default width and height is almost 95% */
    fun modifyWindowsParamsAndShow(dialog: AlertDialog, width: Int, height: Int) {
        dialog.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog.show() // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
//        dialog.window?.attributes = lWindowParams
        dialog.window!!.setLayout(width, height)
    }


    fun initDisplayMetrics(context: Context) {
        displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
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

    /**Download manager event listener methods*/

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
        if (newConfig.orientation !=currentOrientation) {

            super.onConfigurationChanged(newConfig)

        }
        currentOrientation = newConfig.orientation
        Log.i("Orientation","current :: $currentOrientation")
    }

}