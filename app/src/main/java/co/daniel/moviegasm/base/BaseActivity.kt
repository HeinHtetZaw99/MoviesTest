package co.daniel.moviegasm.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Insets
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
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

    abstract fun loadData()

    abstract fun initUI()


    private var displayMetrics: DisplayMetrics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

//        handleRxErrors()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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

    /**
     * This is for restricting the appbar draggable behavior
     * @param  appBarLayout -> the appBar that is needed to take action on
     */
    protected fun disableAppBar(appBarLayout: AppBarLayout) {
        lockAppBarClosed(appBarLayout)
        changeAppbarLayoutDraggableBehavior(appBarLayout, false)
    }

    /**Method for changing appBar Draggable or !Draggable */
    fun changeAppbarLayoutDraggableBehavior(appBarLayout: AppBarLayout, isDraggable: Boolean) {
        if (appBarLayout.layoutParams != null) {
            val layoutParams =
                appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
            val appBarLayoutBehaviour =
                AppBarLayout.Behavior()
            appBarLayoutBehaviour.setDragCallback(object :
                AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return isDraggable
                }
            })
            layoutParams.behavior = appBarLayoutBehaviour
        }
    }


    /**Method for shrinking appBar to ActionBar size
     * @param mAppBarLayout -> the appBar that is needed to take action on
     */
    fun lockAppBarClosed(mAppBarLayout: AppBarLayout) {
        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        // Calculate ActionBar height
        var actionBarHeight = 0
        val tv = TypedValue()
        if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, displaymetrics)
        }
        mAppBarLayout.setExpanded(false, true)
        mAppBarLayout.isActivated = false
        val lp =
            mAppBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        lp.height = actionBarHeight
    }

    /**Method for shrinking appBar to ActionBar size
     * @param mAppBarLayout -> the appBar that is needed to take action on
     * @param overrideParams -> if others  -> the appBar will be resized to desire ratio of the screen height ( DEFAULT is 65 % )
     * 1 -> the appBar's height will be WRAP_CONTENT
     */
    fun unlockAppBarOpen(mAppBarLayout: AppBarLayout, overrideParams: Double) {
        mAppBarLayout.setExpanded(true, true)
        mAppBarLayout.isActivated = true
        val lp =
            mAppBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        if (overrideParams != 1.0) {
            lp.height = getScreenHeight(overrideParams)
        } else {
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        mAppBarLayout.layoutParams = lp
    }


    /**Method for showing toast with customized layout
     * @param toastText -> text to be displayed
     * @param layout -> layout id
     * @param duration  -> duration of the toast
     */
    fun showCustomToast(
        context: Context?,
        toastText: String?,
        @LayoutRes layout: Int,
        duration: Int
    ) { // Get the custom layout view.
        val toastView: View = createView(layout)
        val toastTv =
            toastView.findViewById<TextView>(R.id.toastTv)
        toastTv.text = toastText
        val toast = Toast(context).apply {
            this.view = toastView // Set custom view in toast.
            this.duration = duration
            this.setGravity(Gravity.BOTTOM, 0, 80)
        }

        toast.show()
    }

    fun handleRxErrors() {

        RxJavaPlugins.setErrorHandler(emptyConsumer())
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                e.cause?.let {
                    Thread.currentThread()
                        .uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), it)
                    return@setErrorHandler
                }
            }
            if ((e is IOException) || (e is SocketException)) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if ((e is NullPointerException) || (e is IllegalArgumentException)) {
                // that's likely a bug in the application
                Thread.currentThread()
                    .uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread()
                    .uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            Timber.e("Undeliverable exception received, not sure what to do : $e")
        }
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


    fun setAllParentsClip(view: View, enabled: Boolean) {
        var view = view
        while (view.parent != null && view.parent is ViewGroup) {
            val viewGroup = view.parent as ViewGroup
            viewGroup.clipChildren = enabled
            viewGroup.clipToPadding = enabled
            view = viewGroup
        }
    }

}