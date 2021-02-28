package dev.lucasvillaverde.recipeapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object DeviceUtils {
    fun hasInternet(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            cm.getNetworkCapabilities(cm.activeNetwork)
                ?.let {
                    return it.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                }

            return false
        }

        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting!!
    }


}