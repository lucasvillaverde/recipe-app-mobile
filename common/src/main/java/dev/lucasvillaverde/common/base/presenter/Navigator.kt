package dev.lucasvillaverde.common.base.presenter

import android.os.Bundle
import androidx.navigation.NavController

interface Navigator {
    var navController: NavController

    fun navigateToDirection(navDirection: NavDirection)
}