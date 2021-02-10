package dev.lucasvillaverde.recipeapp.utils

import android.content.Context
import android.net.ConnectivityManager

object DeviceUtils {
    fun hasInternet(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting!!
    }
}