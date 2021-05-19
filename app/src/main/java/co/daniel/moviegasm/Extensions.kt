package co.daniel.moviegasm

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images.Media
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.daniel.moviegasm.base.BaseRecyclerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber
import java.lang.reflect.Method
import kotlin.reflect.jvm.reflect

fun getPrivateMethod(
    methodName: String, parentClass: Class<*>,
    vararg arguments: Class<*>
): Method {
    return parentClass.getDeclaredMethod(methodName, *arguments)
        .apply { isAccessible = true }
}

//Extension Function for showing image
fun ImageView.show(imageUrl: String, @DrawableRes placeHolderImageRes: Int) {
    Glide.with(this)
        .load(imageUrl)
        .apply(
            RequestOptions.fitCenterTransform().placeholder(placeHolderImageRes).error(
                placeHolderImageRes
            )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(this)
}

//Extension Function for showing image
fun ImageView.show(@DrawableRes imageRes: Int) {
    Glide.with(this)
        .load(imageRes)
        .apply(RequestOptions.fitCenterTransform().diskCacheStrategy(DiskCacheStrategy.ALL))
        .into(this)
}

//Extension Function for showing image
fun ImageView.showIcon(@DrawableRes imageRes: Int) {
    Glide.with(this)
        .load(imageRes)
        .into(this)
}

//Extension Function for showing image
fun ImageView.show(imageRes: Uri?, fallback: String) {
    if (imageRes == null)
        this.show(fallback)
    else
        Glide.with(this)
            .load(imageRes)
            .apply(RequestOptions.fitCenterTransform().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(this)
}

//Extension Function for showing image
fun ImageView.show(imageBitmap: Bitmap?) {
    Glide.with(this)
        .load(imageBitmap)
        .apply(RequestOptions.fitCenterTransform().diskCacheStrategy(DiskCacheStrategy.ALL))
        .into(this)
}

//Extension Function for showing image
fun ImageView.show(imageRes: String) {
    Glide.with(this)
        .load(imageRes)
        .apply(
            RequestOptions.placeholderOf(R.drawable.dummy_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(this)
}

fun ImageView.showThumbnail(videoUrl: String) {
    Glide.with(this)
        .load(videoUrl)
        .thumbnail(Glide.with(context).load(videoUrl))
        .apply(
            RequestOptions.fitCenterTransform().placeholder(R.drawable.dummy_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(this)
}


//Extension Function for showing image
fun ImageView.showWithoutCenterCrop(imageUrl: String, @DrawableRes placeHolderImageRes: Int) {
    Glide.with(this)
        .load(imageUrl)
        .apply(
            RequestOptions().placeholder(placeHolderImageRes)
                .error(placeHolderImageRes).diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .into(this)
}

fun RecyclerView.configure(context: Context, adapter: BaseRecyclerAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.configure(context: Context, adapter: PagedListAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context)
}

fun RecyclerView.configureWithGrid(
    context: Context, spanCount: Int,
    adapter: BaseRecyclerAdapter<*, *>
) {
    this.layoutManager = GridLayoutManager(context, spanCount)
    this.adapter = adapter
}

fun RecyclerView.configureWithHorizontal(context: Context, adapter: BaseRecyclerAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.configureWithReverseLayout(context: Context, adapter: BaseRecyclerAdapter<*, *>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true).apply {
        stackFromEnd = true;
    }
}


fun Context.getBitmap(uri: Uri?): Bitmap? {
    if (uri == null)
        return null
    val bitmap = BitmapFactory.decodeFile(uri.path.toString())
    Timber.e("loading for crop $uri : @$bitmap")
    if (bitmap != null)
        return bitmap
    else {
        val filePath = arrayOf(Media.DATA)
        val cursor = contentResolver.query(uri, filePath, null, null, null);
        cursor!!.moveToFirst();
        val imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]))
        val retryBitmap = BitmapFactory.decodeFile(imagePath)
        Timber.e("loading for crop $imagePath : @$retryBitmap retry")
        if (retryBitmap != null)
            return retryBitmap
    }
    return null
}

/**Method for modifying and overriding alertDialogs width and height as default width and height is almost 95% */
fun modifyWindowsParamsAndShow(dialog: Dialog, width: Int, height: Int) {
    dialog.show() // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
//        dialog.window?.attributes = lWindowParams
    dialog.window!!.setLayout(width, height)
}

fun NavController.navigate(viewGroup: ViewGroup, @IdRes destinationID: Int, bundle: Bundle) {
    try {
        this.navigate(destinationID, bundle)
    } catch (e: IllegalArgumentException) {
        Navigation.findNavController(viewGroup).navigate(destinationID, bundle)
    }
}

/** Only for actions that is not necessarily need to take further action in catch block
 */
fun tryInSilence(action: () -> Unit) {
    try {
        action.invoke()
    } catch (e: Exception) {
        Timber.e(action.reflect()?.name ?: "APP_TAG", e)
    }
}

fun ComponentActivity.showShortToast(message: CharSequence) {
    if (message.trim().isNotEmpty())
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}


fun ComponentActivity.showShortToast(@StringRes resId: Int) {
    Toast.makeText(applicationContext, resId, Toast.LENGTH_SHORT).show()
}

fun ComponentActivity.showLongToast(message: CharSequence) {
    if (message.trim().isNotEmpty())
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}

fun ComponentActivity.showLongToast(@StringRes resId: Int) {
    Toast.makeText(applicationContext, resId, Toast.LENGTH_LONG).show()
}

fun Fragment.showShortToast(message: CharSequence) {
    if (message.trim().isNotEmpty())
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showShortToast(@StringRes resId: Int) {

    Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
}

fun Fragment.showLongToast(message: CharSequence) {
    if (message.trim().isNotEmpty())
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showLongToast(@StringRes resId: Int) {
    Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
}

fun Toolbar.addBackNavButton(activity : AppCompatActivity) {
    activity.setSupportActionBar(this)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    activity.supportActionBar?.setDisplayShowHomeEnabled(true)
    activity.supportActionBar?.title = ""
//    this.navigationIcon = ContextCompat.getDrawable(activity , backIcon)
    this.setNavigationOnClickListener {
        //What to do on back clicked
        activity.onBackPressed()
    }
}