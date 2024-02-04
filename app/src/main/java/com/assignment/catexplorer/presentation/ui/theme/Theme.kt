package com.assignment.catexplorer.presentation.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    background = Color.Black,
    surface = Color.Black,
    onSurface = LightPrimary,
    onBackground = LightPrimary,
    primary = LightPrimary,
    secondary = LightSecondary
)

private val LightColorPalette = lightColors(
    background = Color.White,
    surface = Color.White,
    onSurface = DarkPrimary,
    onBackground = DarkPrimary,
    primary = DarkPrimary,
    secondary = DarkSecondary,
)

@Composable
fun CatExplorerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Log.d("ThemeCheck", "${isSystemInDarkTheme()}")
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}