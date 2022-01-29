package dev.lucasvillaverde.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = CleanBlue,
    primaryVariant = CleanBlue,
    onPrimary = Color.Black,
    secondary = CleanGreen,
    secondaryVariant = CleanGreen,
    onSecondary = Color.Black,
    error = Color.Red,
    onBackground = Color.Black,
)

private val DarkThemeColors = darkColors(
    primary = CleanBlueDark,
    primaryVariant = CleanBlueDark,
    onPrimary = Color.White,
    secondary = CleanGreenDark,
    onSecondary = Color.White,
    error = Color.Red,
    onBackground = Color.White
)

@Composable
fun RecipeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = RecipeAppTypography,
        content = content
    )
}
