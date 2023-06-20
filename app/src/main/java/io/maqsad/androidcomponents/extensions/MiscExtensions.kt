package io.maqsad.androidcomponents.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.provider.MediaStore
import android.util.Log
import android.view.WindowManager
import androidx.annotation.Size
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material.DrawerState
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

suspend fun DrawerState.openIt() {
    if (!this.isOpen) this.open()
}

suspend fun DrawerState.closeIt() {
    if (!this.isClosed) this.close()
}

fun String.getCookieValue(): String {
    val uri: Uri = Uri.parse(this)
    val policyString: String? = uri.getQueryParameter("Policy")
    val signatureString: String? = uri.getQueryParameter("Signature")
    val keyPairIdString: String? = uri.getQueryParameter("Key-Pair-Id")
    var cookieValue = ""
    cookieValue += "CloudFront-Policy=$policyString;"
    cookieValue += "CloudFront-Key-Pair-Id=$keyPairIdString;"
    cookieValue += "CloudFront-Signature=$signatureString;"

    return cookieValue
}

fun String.downloadsMapKey(): String? {
    return this.split(".m3u8").firstOrNull()
}

fun Uri.downloadsMapKey(): String? {
    return this.toString().split(".m3u8").firstOrNull()
}

//fun Activity.createSignedMediaSource(videoUrl: String): HlsMediaSource {
//    val uri: Uri = Uri.parse(videoUrl)
//    val policyString: String? = uri.getQueryParameter("Policy")
//    val signatureString: String? = uri.getQueryParameter("Signature")
//    val keyPairIdString: String? = uri.getQueryParameter("Key-Pair-Id")
//    var cookieValue = ""
//    cookieValue += "CloudFront-Policy=$policyString;"
//    cookieValue += "CloudFront-Key-Pair-Id=$keyPairIdString;"
//    cookieValue += "CloudFront-Signature=$signatureString;"
//
//    // httpDataSourceFactory
//    val httpDataSourceFactory: HttpDataSource.Factory =
//        DefaultHttpDataSource.Factory()
//            .setDefaultRequestProperties(mapOf("Cookie" to cookieValue))
//            .setAllowCrossProtocolRedirects(true)
//
//    val dataSourceFactory = DefaultDataSource.Factory(this, httpDataSourceFactory)
//    return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(videoUrl))
//}

fun requiredPermissions(): List<String> {
    return when {
        SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
            listOf(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
        }

        else -> {
            listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }
}

fun Activity.allPermissionsGranted(): Boolean {
    return when {
        SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
            hasPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
        }

        else -> {
            hasPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }
}
//
//fun Activity.getSignedDataSource(videoUrl: String): DataSource.Factory {
//    val uri: Uri = Uri.parse(videoUrl)
//    val policyString: String? = uri.getQueryParameter("Policy")
//    val signatureString: String? = uri.getQueryParameter("Signature")
//    val keyPairIdString: String? = uri.getQueryParameter("Key-Pair-Id")
//    var cookieValue = ""
//    cookieValue += "CloudFront-Policy=$policyString;"
//    cookieValue += "CloudFront-Key-Pair-Id=$keyPairIdString;"
//    cookieValue += "CloudFront-Signature=$signatureString;"
//
//    // httpDataSourceFactory
//    val httpDataSourceFactory: HttpDataSource.Factory =
//        DefaultHttpDataSource.Factory().setDefaultRequestProperties(mapOf("Cookie" to cookieValue))
//            .setAllowCrossProtocolRedirects(true)
//
//    return DefaultDataSource.Factory(this, httpDataSourceFactory)
//}

//fun String.getCookieValue(): Map<String, String> {
//    val uri: Uri = Uri.parse(this)
//    val policyString: String? = uri.getQueryParameter("Policy")
//    val signatureString: String? = uri.getQueryParameter("Signature")
//    val keyPairIdString: String? = uri.getQueryParameter("Key-Pair-Id")
//    var cookieValue = ""
//    cookieValue += "CloudFront-Policy=$policyString;"
//    cookieValue += "CloudFront-Key-Pair-Id=$keyPairIdString;"
//    cookieValue += "CloudFront-Signature=$signatureString;"
//    return mapOf("Cookie" to cookieValue)
//}

fun Activity.hasPermissions(@Size(min = 1) vararg perms: String): Boolean {
    // Always return true for SDK < M, let the system deal with the permissions
    for (perm in perms) {
        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

fun Fragment.hasPermissions(@Size(min = 1) vararg perms: String): Boolean {
    // Always return true for SDK < M, let the system deal with the permissions
    for (perm in perms) {
        if (ContextCompat.checkSelfPermission(
                requireContext(), perm
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
    }
    return true
}


inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1, options: Bundle? = null, noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <T> Continuation<T>.safeResume(value: T, onExceptionCalled: () -> Unit) {
    if (this is CancellableContinuation) {
        if (isActive) resume(value)
        else onExceptionCalled()
    } else throw Exception("Must use suspendCancellableCoroutine instead of suspendCoroutine")
}

inline fun <reified T : Any> Fragment.launchActivity(
    options: Bundle? = null, noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(context!!)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null, noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

fun afterDelay(delay: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(block, delay)
}

fun Activity.isDevApp(): Boolean {
    return packageName == "io.maqsad.dev"
}

fun Activity.openUrl(url: String) {
//    if (StatsigManager.checkGate(FeatureGates.inAppBrowser)) {
    openUrlInsideApp(url)
//        return
//    }
//    openUrlExternally(url, extraText)
}

fun Context.openSafeUrl(url: String) {
    if (url.isBlank() || !url.contains("https")) return
//    if (StatsigManager.checkGate(FeatureGates.inAppBrowser)) {
    openUrlInsideApp(url)
//        return
//    }
//    openUrlExternally(url)
}

fun Activity.openUrlInsideApp(url: String) {
    try {
        val uri = Uri.parse(url)
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, uri)
    } catch (e: Exception) {
        openUrlExternally(url)
    }
}

fun Context.openUrlInsideApp(url: String) {
    try {
        val uri = Uri.parse(url)
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, uri)
    } catch (e: Exception) {
        openUrlExternally(url)
    }
}

fun Activity.openUrlExternally(url: String) {
    val uri = Uri.parse(url)
    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)
    val customTabsIntent = builder.build()
    val browserIntent =
        Intent().setAction(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setDataAndType(Uri.fromParts("https", "", null), "text/plain")

    var possibleBrowsers: List<ResolveInfo>
    if (SDK_INT >= Build.VERSION_CODES.M) {
        possibleBrowsers = this.packageManager.queryIntentActivities(
            browserIntent, PackageManager.MATCH_DEFAULT_ONLY
        )
        if (possibleBrowsers.isEmpty()) {
            possibleBrowsers =
                this.packageManager.queryIntentActivities(browserIntent, PackageManager.MATCH_ALL)
        }
    } else {
        possibleBrowsers = this.packageManager.queryIntentActivities(
            browserIntent, PackageManager.MATCH_DEFAULT_ONLY
        )
    }
    if (possibleBrowsers.isNotEmpty()) {
        customTabsIntent.intent.setPackage(possibleBrowsers[0].activityInfo.packageName)
        customTabsIntent.launchUrl(this, uri)
    } else {
        val browserIntent2 = Intent(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setDataAndType(Uri.fromParts("https", "", null), "text/plain")
            .setData(uri)
        this.startActivity(browserIntent2)
    }
}

fun Context.openUrlExternally(url: String, extraText: String = "") {
    val uri = Uri.parse(url)
    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)
    val customTabsIntent = builder.build()
    val browserIntent =
        Intent().setAction(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE)
            .setDataAndType(Uri.fromParts("http", "", null), "text/plain")
            .putExtra(Intent.EXTRA_TEXT, extraText)
    var possibleBrowsers: List<ResolveInfo>
    if (SDK_INT >= Build.VERSION_CODES.M) {
        possibleBrowsers = this.packageManager.queryIntentActivities(
            browserIntent, PackageManager.MATCH_DEFAULT_ONLY
        )
        if (possibleBrowsers.isEmpty()) {
            possibleBrowsers =
                this.packageManager.queryIntentActivities(browserIntent, PackageManager.MATCH_ALL)
        }
    } else {
        possibleBrowsers = this.packageManager.queryIntentActivities(
            browserIntent, PackageManager.MATCH_DEFAULT_ONLY
        )
    }
    if (possibleBrowsers.isNotEmpty()) {
        customTabsIntent.intent.setPackage(possibleBrowsers[0].activityInfo.packageName)
        customTabsIntent.launchUrl(this, uri)
    } else {
        val browserIntent2 = Intent(Intent.ACTION_VIEW, uri)
        this.startActivity(browserIntent2)
    }
}

inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

inline fun <T1 : Any, T2 : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> Unit) {
    if (p1 != null && p2 != null) block(p1, p2)
}

fun Uri.toStringPath(activity: Activity?): String? {
    return try {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = activity?.applicationContext?.contentResolver?.query(
            this, filePathColumn, null, null, null
        )
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val picturePath: String? = cursor?.getString(columnIndex!!)
        cursor?.close()
        picturePath
    } catch (e: Exception) {
        "toStringPath".printLog(e)
        ""
    }
}

fun Uri.toStringPath(context: Context): String? {
    return try {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.applicationContext?.contentResolver?.query(
            this, filePathColumn, null, null, null
        )
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
        val picturePath: String? = cursor?.getString(columnIndex!!)
        cursor?.close()
        picturePath
    } catch (e: Exception) {
        "toStringPath".printLog(e)
        ""
    }
}

fun JSONObject.toMap(): HashMap<String, String> {
    return Gson().fromJson(this.toString(), HashMap::class.java) as HashMap<String, String>
}

fun Bundle.toMap(): Map<String, String> {
    val map: MutableMap<String, String> = HashMap()
    val ks = this.keySet()
    val iterator: Iterator<String> = ks.iterator()
    while (iterator.hasNext()) {
        val key = iterator.next()
        map[key] = this.get(key).toString()
    }
    return map
}

fun Bundle.toHashMap(): HashMap<String, Any?> {
    val map: HashMap<String, Any?> = hashMapOf()
    val ks = this.keySet()
    val iterator: Iterator<String> = ks.iterator()
    while (iterator.hasNext()) {
        val key = iterator.next()
        map[key] = this.get(key).toString()
    }
    return map
}


fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit, doInBackground: () -> R, onPostExecute: (R) -> Unit
) = launch {
    onPreExecute()
    val result = withContext(Dispatchers.IO) {
        doInBackground()
    }
    onPostExecute(result)
}

fun Bitmap.getUriFromBitmap(activity: Activity): Uri {
    val file = File(activity.cacheDir, "Temp bitmap")
    if (file.exists()) file.delete()
    file.createNewFile()
    val fileOutputStream = file.outputStream()
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val bytearray = byteArrayOutputStream.toByteArray()
    fileOutputStream.write(bytearray)
    fileOutputStream.flush()
    fileOutputStream.close()
    byteArrayOutputStream.close()
    return file.toUri()
}


inline fun <reified T : Any> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}

//fun String.parseProductByType(productType: ProductType): GenericProduct {
//    return when (productType) {
//        ProductType.PAST_PAPER -> Gson().fromJson(this, PastPaperModel::class.java)
//        ProductType.QUESTION_BANK -> Gson().fromJson(this, PastPaperModel::class.java)
//        ProductType.DOUBT_SOLVE -> Gson().fromJson(this, ProductModel::class.java)
//        ProductType.TEST_GRADE -> Gson().fromJson(this, ProductTypeModel::class.java)
//        ProductType.MDCAT -> Gson().fromJson(this, NewProductModel::class.java)
//    }
//}

inline fun <reified T : Any> String.fromSafeJson(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T : Any> String.fromSafeJsonList(): List<T>? {
    return try {
        Gson().fromJson(this, object : TypeToken<List<T>>() {}.type)
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T : Any> List<T>?.toSafeJsonList(): String {
    if (this.isNullOrEmpty()) return ""
    return Gson().toJson(this, object : TypeToken<List<T>>() {}.type)
}

inline fun <reified T : Any> T.toJson(): String {
    return Gson().toJson(this, T::class.java)
}

inline fun <reified T : Any> T?.toSafeJson(): String {
    if (this == null) return ""
    return Gson().toJson(this, T::class.java)
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun String.utcToFormat(desiredFormat: String): String {
    var dateStrConverted = ""
    val formatOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    try {
        formatOriginal.parse(this)?.let {
            dateStrConverted = SimpleDateFormat(desiredFormat, Locale.US).format(it)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return dateStrConverted
}

fun Context.getDrawableFromAssets(name: String): Drawable? {
    return try {
        // get input stream
        val ims: InputStream = this.assets.open("$name.png")
        // load image as Drawable
        Drawable.createFromStream(ims, null)
        // set image to ImageView
    } catch (ex: IOException) {
        null
    }
}

fun Uri?.getIntParameter(key: String, default: Int = 0): Int {
    return this?.getQueryParameter(
        key
    )?.toIntOrNull() ?: default
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

fun Intent?.getBoolean(key: String, default: Boolean = false): Boolean {
    return this?.getBooleanExtra(key, default) ?: default
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key, T::class.java
    )

    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}


fun Activity.blockUI() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.unblockUI() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun isDateToday(date: Calendar): Boolean {
    val today = Calendar.getInstance()
    return date.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            date.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
            date.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
}

fun String.printLog(text: Any?) {
//    if (BuildConfig.DEBUG) {
    Log.e(this, if (text !is String) text.toString() else text)
//    }
}

fun String.printProdLog(text: Any?) {
    Log.e(this, if (text !is String) text.toString() else text)
}

fun Activity.printLog(text: Any?) {
//    if (BuildConfig.DEBUG) {
    Log.e(this::class.java.simpleName, if (text !is String) text.toString() else text)
//    }
}
