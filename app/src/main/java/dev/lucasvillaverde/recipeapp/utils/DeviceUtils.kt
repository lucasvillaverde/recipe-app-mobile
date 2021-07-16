package dev.lucasvillaverde.recipeapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object DeviceUtils {
    fun hasInternet(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cm.getNetworkCapabilities(cm.activeNetwork)
                ?.let {
                    return it.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                        .or(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                }

            return false
        }

        return cm.isDefaultNetworkActive
    }
}