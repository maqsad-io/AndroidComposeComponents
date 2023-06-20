@file:Suppress("DEPRECATION")

package io.maqsad.androidcomponents.extensions


import android.app.Activity
import android.content.*
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Spanned
import androidx.annotation.DimenRes
import androidx.annotation.IntRange
import androidx.compose.ui.graphics.Color
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import io.maqsad.androidcomponents.utils.SharedPrefKeys
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.reflect.KClass

fun Activity.getPixels(@DimenRes res: Int): Int {
    return resources.getDimensionPixelSize(res)
}

fun Float.percentage(total: Float): Float {
    return (this / (if (total == 0f) 1f else total)) * 100
}


fun Int?.orDefault(default: Int = 0): Int {
    return this ?: default
}

fun <T> T?.orDefault(default: T): T {
    return this ?: default
}

fun String?.orDefault(default: String = ""): String {
    return this ?: default
}

fun Boolean?.orDefault(default: Boolean = false): Boolean {
    return this ?: default
}


fun String.color(): Color? {
    return try {
        Color(android.graphics.Color.parseColor(this))
    } catch (e: Exception) {
        null//ColorPrimary
    }
}

fun Boolean.toInt() = if (this) 1 else 0

fun Int.percentage(total: Int): Int {
    val percentage =
        ((this.toFloat() / (if (total.toFloat() == 0f) 1f else total.toFloat())) * 100).toInt()
    return min(percentage, 100)
}


fun Activity.userId(): String {
    return getSharedPrefString(SharedPrefKeys.userID)
}

fun Context.userId(): String {
    return getSharedPrefString(SharedPrefKeys.userID)
}

fun Fragment.userId(): String {
    return getSharedPrefString(SharedPrefKeys.userID)
}

fun Activity.copyToClipboard(text: String) {
    val clipboard: ClipboardManager =
        this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied text", text)
    clipboard.setPrimaryClip(clip)
//    showToastShort("Link copied to clipboard!")
}

fun Context.copyToClipboard(text: String) {
    try {
        val clipboard: ClipboardManager =
            this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied text", text)
        clipboard.setPrimaryClip(clip)
        "copyToClipboard".printLog("copied")
    } catch (e: Exception) {
        "copyToClipboard".printLog(e)
    }
}
//    showToastShort("Link copied to clipboard!")

fun Fragment.getIntentBoolean(key: String): Boolean {
    return activity?.intent?.getBooleanExtra(key, false) ?: false
}

fun Activity.getIntentBoolean(key: String): Boolean {
    return intent?.getBooleanExtra(key, false) ?: false
}

fun Activity.getIntentBoolean(key: String, default: Boolean): Boolean {
    return intent?.getBooleanExtra(key, default) ?: default
}

fun Intent?.getIntentBoolean(key: String): Boolean {
    return this?.getBooleanExtra(key, false) ?: false
}

//fun AssessmentsTestActivity.saveUpdatedUserProgress(
//    progressData: UpdatedUserProgressModel,
//    subjectId: String,
//    topicId: String,
//    difficultyId: String?,
//) {
//    val preferences: SharedPreferences =
//        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
//    val editor = preferences.edit()
//    editor.putInt(subjectId, progressData.fetchUpdateUserProgress.subject)
//    editor.putInt(topicId, progressData.fetchUpdateUserProgress.topic)
//    editor.putInt(difficultyId, progressData.fetchUpdateUserProgress.difficulty)
//    editor.apply()
//}


@IntRange(from = 0, to = 3)
fun Activity.getConnectionType(): Int {
    var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
    val cm =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cm?.run {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        result = 2
                    }

                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        result = 1
                    }

                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                        result = 3
                    }
                }
            }
        }
    } else {
        cm?.run {
            cm.activeNetworkInfo?.run {
                when (type) {
                    ConnectivityManager.TYPE_WIFI -> {
                        result = 2
                    }

                    ConnectivityManager.TYPE_MOBILE -> {
                        result = 1
                    }

                    ConnectivityManager.TYPE_VPN -> {
                        result = 3
                    }
                }
            }
        }
    }
    return result
}

//fun MyApplication.getConnectionTypeName(): String {
//    return when (getConnectionType()) {
//        1 -> "mobile-data"
//        2 -> "wifi"
//        else -> "none"
//    }
//}

//@IntRange(from = 0, to = 3)
//fun MyApplication.getConnectionType(): Int {
//    var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
//    val cm =
//        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//        cm?.run {
//            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
//                when {
//                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
//                        result = 2
//                    }
//
//                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
//                        result = 1
//                    }
//
//                    hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
//                        result = 3
//                    }
//                }
//            }
//        }
//    } else {
//        cm?.run {
//            cm.activeNetworkInfo?.run {
//                when (type) {
//                    ConnectivityManager.TYPE_WIFI -> {
//                        result = 2
//                    }
//
//                    ConnectivityManager.TYPE_MOBILE -> {
//                        result = 1
//                    }
//
//                    ConnectivityManager.TYPE_VPN -> {
//                        result = 3
//                    }
//                }
//            }
//        }
//    }
//    return result
//}

//fun Activity.getPlaybackSpeedText(value: Float): String {
//    return when (value) {
//        0.5f -> resources.getString(R.string.speed_0_5)
//        1f -> resources.getString(R.string.normal)
//        1.5f -> resources.getString(R.string.speed_1_5)
//        2f -> resources.getString(R.string.speed_2_0)
//        2.5f -> resources.getString(R.string.speed_2_5)
//        else -> ""
//    }
//}
//
//fun Fragment.getPlaybackSpeedText(value: Float): String {
//    return when (value) {
//        0.5f -> resources.getString(R.string.speed_0_5)
//        1f -> resources.getString(R.string.normal)
//        1.5f -> resources.getString(R.string.speed_1_5)
//        2f -> resources.getString(R.string.speed_2_0)
//        2.5f -> resources.getString(R.string.speed_2_5)
//        else -> ""
//    }
//}
//
//fun <T> Iterator<T>.toList(): List<T> {
//    val list: MutableList<T> = ArrayList()
//    this.forEachRemaining { e: T -> list.add(e) }
//    return list
//}


fun String.getResourceId(c: Class<*>): Int {
    return try {
        val idField = c.getDeclaredField(this)
        idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }
}

fun Activity.saveSelectedSubject(subjectId: String, name: String) {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putString("SELECTED_SUBJECT_ID", subjectId)
    editor.putString("SELECTED_SUBJECT_NAME", name)
    editor.apply()
}

fun Activity.getSelectedSubjectId(): String {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences.getString("SELECTED_SUBJECT_ID", "")!!
}

fun Activity.getSelectedSubjectName(): String {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences.getString("SELECTED_SUBJECT_NAME", "") ?: ""
}

fun Long.toDateString(format: String? = null): String {
    return SimpleDateFormat(
        format ?: "dd MMM yyyy", Locale.US
    ).format(Date(this))
}

fun Long.toIsoDateStringInUTC(): String {
    val sdf = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
    )
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(Date(this))
}
//TopicId: c8ecd79b-3325-41ab-b513-e8838b924c35
//SubjectId: 8f236a25-1401-496e-ae94-be33c97522c3

fun Double.roundTo(decimalPlace: Int): Double {
    return BigDecimal(this).setScale(decimalPlace, RoundingMode.HALF_EVEN).toDouble()
}

fun Float.roundTo(decimalPlace: Int): Float {
    return BigDecimal(this.toDouble()).setScale(decimalPlace, RoundingMode.HALF_EVEN).toFloat()
}

fun String.frequency(text: String): Int {
    return this.count { c -> c.toString() == text }
}

fun Activity.pxToDp(px: Float): Float {
    return px / this.resources.displayMetrics.density
}

fun Activity.dpToPx(dp: Int): Float {
    return dp * this.resources.displayMetrics.density
}

fun Fragment.dpToPx(dp: Int): Float {
    return dp * this.resources.displayMetrics.density
}

fun Activity.sdpToPx(sdp: Float): Float {
    return sdp * this.resources.displayMetrics.density
}

fun Double?.parseIntoMinutes(): String {
    if (this == null) return "0:00 min"
    val minutes = (this / 60).toInt()
    val seconds = (this % 60).toInt()
    val minutesString = if (minutes <= 9) "0${minutes}" else "$minutes"
    val secondsString = if (seconds <= 9) "0${seconds}" else "$seconds"
    return "${minutesString}:${secondsString} min"
}

fun Long.parseIntoMinutes(): String {
    val minutes = (this / 60).toInt()
    val seconds = (this % 60).toInt()
    val minutesString = if (minutes <= 9) "0${minutes}" else "$minutes"
    val secondsString = if (seconds <= 9) "0${seconds}" else "$seconds"
    return "${minutesString}:${secondsString}"
}

fun Long.parseIntoHoursMinutes(): String {
    val hours = (this / 3600).toInt()
    val minutes = ((this % 3600) / 60).toInt()
    val seconds = (this % 60).toInt()
    val minutesString = if (minutes <= 9) "0${minutes}" else "$minutes"
    val secondsString = if (seconds <= 9) "0${seconds}" else "$seconds"
    val hoursString = if (hours <= 9) "0${hours}" else "$hours"
    return "${hoursString}:${minutesString}:${secondsString}"
}

fun Long.millsToMinutes(): String {
    val totalSeconds = this / 1000
    val minutes = (totalSeconds / 60).toInt()
    val seconds = (totalSeconds % 60).toInt()
    val minutesString = if (minutes <= 9) "0${minutes}" else "$minutes"
    val secondsString = if (seconds <= 9) "0${seconds}" else "$seconds"
    return "${minutesString}:${secondsString}"
}

fun Double.getFormattedNumber(): String {
    if (this < 1000) return "" + this
    val exp = (ln(this) / ln(1000.0)).toInt()
    return String.format("%.1f%c", this / 1000.0.pow(exp.toDouble()), "KMGTPE"[exp - 1])
}

fun Int.getFormattedNumber(): String {
    if (this < 1000) return "" + this
    val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
    return String.format("%.1f%c", this / 1000.0.pow(exp.toDouble()), "KMGTPE"[exp - 1])
}

fun Double.toHours(): Double {
    val minutes = (this / 60)
    val number3digits: Double = String.format("%.3f", (minutes / 60)).toDouble()
    val number2digits: Double = String.format("%.2f", number3digits).toDouble()
    return String.format("%.1f", number2digits).toDouble()
}

fun Int.getSerialNumberString(): String {
    if (this == 0) return this.toString()
    return if (this in -9..9) "0${this}" else "$this"
}

fun Long.divide(other: Int): Double {
    return this.toDouble() / other.toDouble()
}

fun Activity.getDoubtQueryStartTime(): Long? {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val endTime = preferences.getLong("DOUBT_QUERY_START_TIME", 0L)
    return if (endTime != 0L) endTime else null
}

fun Activity.saveDoubtQueryStartTime(startTime: Long?) {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putLong("DOUBT_QUERY_START_TIME", startTime ?: 0L)
    editor.apply()
}

fun JSONObject.getBooleanIfExists(key: String): Boolean {
    return if (this.has(key)) this.getBoolean(key) else false
}

fun JSONObject.getStringIfExists(key: String): String {
    return if (this.has(key)) this.getString(key) else "[]"
}

fun Date.utcToLocal(): Date {
    return Date(this.time + TimeZone.getDefault().getOffset(this.time))
}

fun Date.localToUtc(): Date {
    return Date(this.time - TimeZone.getDefault().getOffset(this.time))
}


fun Activity.addSharedPrefBoolean(key: String, value: Boolean) {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putBoolean(key, value)
    editor.apply()
}

fun Context.addSharedPrefBoolean(key: String, value: Boolean) {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putBoolean(key, value)
    editor.apply()
}

fun Activity.getSharedPrefBoolean(key: String, default: Boolean = false): Boolean {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences.getBoolean(key, default)
}

fun Activity.sharedPrefValueExists(key: String): Boolean {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences.contains(key)
}

fun Fragment.addSharedPrefBoolean(key: String, value: Boolean) {
    val preferences: SharedPreferences? =
        activity?.applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putBoolean(key, value)
    editor?.apply()
}

fun Fragment.getSharedPrefBoolean(key: String, default: Boolean = false): Boolean {
    val preferences: SharedPreferences? =
        activity?.applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getBoolean(key, default) ?: default
}

fun Fragment.addSharedPrefString(key: String, value: String?) {
    val preferences: SharedPreferences? =
        activity?.applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putString(key, value)
    editor?.apply()
}

fun Context?.addSharedPrefString(key: String, value: String?) {
    val preferences: SharedPreferences? =
        this?.applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putString(key, value)
    editor?.apply()
}

fun Activity.addSharedPrefString(key: String, value: String) {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putString(key, value)
    editor?.apply()
}

fun Activity.getSharedPrefString(key: String, defValue: String = ""): String {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getString(key, defValue) ?: ""
}

fun Context.getSharedPrefBoolean(key: String): Boolean {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getBoolean(key, false) ?: false
}

fun Context?.getSharedPrefString(key: String, defValue: String = ""): String {
    val preferences: SharedPreferences? =
        this?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getString(key, defValue) ?: ""
}

fun Activity.addSharedPrefFloat(key: String, value: Float) {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putFloat(key, value)
    editor?.apply()
}


fun Activity.getSharedPrefFloat(key: String, defValue: Float): Float {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getFloat(key, defValue) ?: defValue
}

fun Fragment.addSharedPrefInt(key: String, value: Int?) {
    if (value == null) return
    val preferences: SharedPreferences? =
        activity?.applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putInt(key, value)
    editor?.apply()
}


fun Fragment.getSharedPrefInt(key: String, defValue: Int): Int {
    val preferences: SharedPreferences? =
        activity?.applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getInt(key, defValue) ?: defValue
}

fun Activity.addSharedPrefInt(key: String, value: Int?) {
    if (value == null) return
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.putInt(key, value)
    editor?.apply()
}

fun Activity.saveStringListToSharedPreferences(key: String, stringList: List<String>) {
    val joinedString = stringList.joinToString(",")
    addSharedPrefString(key, joinedString)
}

fun Activity.getStringListFromSharedPreferences(key: String): List<String> {
    val joinedString = getSharedPrefString(key)
    return joinedString.split(",")
}


fun Context.saveStringListToSharedPreferences(key: String, stringList: List<String>) {
    val joinedString = stringList.joinToString(",")
    addSharedPrefString(key, joinedString)
}

fun Context.getStringListFromSharedPreferences(key: String): List<String> {
    val joinedString = getSharedPrefString(key)
    return joinedString.split(",")
}

fun Activity.getSharedPrefInt(key: String, defValue: Int): Int {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getInt(key, defValue) ?: defValue
}

fun Context.getSharedPrefInt(key: String, defValue: Int = 0): Int {
    val preferences: SharedPreferences? =
        applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getInt(key, defValue) ?: defValue
}

@Suppress("UNCHECKED_CAST")
inline fun <reified Type> kotlinClass(): KClass<Any> = (Type::class as KClass<Any>)


inline fun <reified T : Any> Activity.addSharedPrefValue(key: String, value: T) {
    val returningClass = kotlinClass<T>()
    val type = (returningClass as Any).javaClass.name
    "HERE".printLog(type)
}

fun Fragment.getSharedPrefString(key: String, defValue: String = ""): String {
    val preferences: SharedPreferences? =
        activity?.applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences?.getString(key, defValue) ?: defValue
}

fun Activity.removeSharedPrefValue(key: String) {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.remove(key)
    editor.apply()
}


fun Context.removeSharedPrefValue(key: String) {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.remove(key)
    editor.apply()
}

fun Fragment.removeSharedPrefValue(key: String) {
    val preferences: SharedPreferences? =
        activity?.applicationContext?.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor? = preferences?.edit()
    editor?.remove(key)
    editor?.apply()
}

fun Activity.fromHtml(text: String): Spanned {
    return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun String.isLastCharacterADigit(): Boolean {
    if (this.isBlank()) return false
    return this[this.length - 1].toString().toIntOrNull() != null
}

fun String.indexExists(index: Int): Boolean {
    return try {
        val d = this[index]
        true
    } catch (e: Exception) {
        false
    }
}

fun Any?.toStringOrEmpty(): String {
    return this?.toString() ?: ""
}

fun String.underline(): Spanned =
    HtmlCompat.fromHtml("<u>$this</u>", HtmlCompat.FROM_HTML_MODE_LEGACY)

fun String.toCamelCase() = split(' ').joinToString(" ", transform = String::capitalize)


fun String.superScript(): String {
    return "<sup>$this</sup>"
}

fun List<*>?.indexExists(index: Int): Boolean {
    return this != null && (index in 0 until this.size)
}

fun <T> List<T>?.safeIndexOf(item: T?): Int {
    return if (this == null || item == null) 0 else max(this.indexOf(item), 0)
}

fun List<*>?.indexDoesNotExist(index: Int): Boolean {
    return this == null || index < 0 || index >= this.size
}

fun Activity.clearAllSharedPrefs() {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.clear()
    editor.apply()
}

fun Context.clearAllSharedPrefs() {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.clear()
    editor.apply()
}

//fun PastPaperModel.toGooglePayProductModel(): GooglePayProductModel {
//    return GooglePayProductModel(
//        productID = this.id,
//        productName = this.name,
//        productPrice = this.price.price.toIntOrNull(),
//        googlePayProductID = this.googlePayProductId,
//        productType = ProductType.PAST_PAPER
//    )
//}

fun <T> ArrayList<T>.extend(otherList: List<T>?) {
    if (otherList == null) return
    this.addAll(otherList)
}


fun List<*>?.size(): Int {
    return this?.size ?: 0
}

fun Int.toBoolean(): Boolean? {
    if (this == 1) return true
    if (this == 0) return false
    return null
}

fun Int.safeDiv(other: Int): Int {
    return this.div(if (other == 0) 1 else other)
}

fun Float.safeDiv(other: Float): Float {
    return this.div(if (other == 0f) 1f else other)
}

fun Int?.toNonNullFloat(): Float {
    return this?.toFloat() ?: 0f
}


fun String.toCreatedDateAndTime(createdAt: String?): String {
    if (this != "") return this
    if (createdAt.isNullOrBlank()) return "Untitled"

    var convertedString = ""
    val formatOriginal = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    try {
        formatOriginal.parse(createdAt)?.let {
            convertedString = SimpleDateFormat("dd MMM, yyyy", Locale.US).format(it)
            convertedString += " • " + SimpleDateFormat(
                "hh:mm aa", Locale.US
            ).format(it.utcToLocal())
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return convertedString

}

fun Double.secondsToDuration(): String {
    val sec = (this % 60).roundToInt()
    val min = (this / 60 % 60).roundToInt()
    return min.padInteger() + ":" + sec.padInteger()
}

fun Int.padInteger(): String {
    return if (this < 10) "0$this" else "$this"
}

fun <T> MutableList<T>.addIfAbsent(obj: T) {
    if (!contains(obj)) add(obj)
}

fun String.addNavParam(key: String, value: String): String {
    return if (value.isNotBlank()) this.replace(key, value) else this
}

fun String.safeSubString(startIndex: Int = 0, endIndex: Int): String {
    return when {
        this.isBlank() -> this
        startIndex !in 0 until this.length -> {
            this
        }

        (endIndex - 1) !in 0 until this.length -> {
            this
        }

        else -> this.substring(startIndex = startIndex, endIndex = endIndex)
    }
}