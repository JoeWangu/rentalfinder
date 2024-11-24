package com.saddict.rentalfinder.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.datastore.preferences.preferencesDataStore
import coil.ImageLoader

fun Context.toastUtil(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastUtilLong(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun everyFirstLetterCapitalize(str: String): String {
    val words = str.split(" ").toMutableList()
    var output = ""
    for (word in words) {
        output += word.capitalize(Locale.current) + " "
    }
    output = output.trim()
    return output
}

val Context.tokenDataStore by preferencesDataStore("app_preferences")

fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val n = cm.activeNetwork
    if (n != null) {
        val nc = cm.getNetworkCapabilities(n)
        //It will check for both wifi and cellular network
        return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        )
    }
    return false
}

object ImageLoaderProvider {
    private var instance: ImageLoader? = null

    fun getInstance(context: Context): ImageLoader {
        return instance ?: synchronized(this) {
            instance ?: ImageLoader.Builder(context)
                .crossfade(true)
                .diskCachePolicy(coil.request.CachePolicy.ENABLED)
                .memoryCachePolicy(coil.request.CachePolicy.ENABLED)
                .build().also { instance = it }
        }
    }
}
