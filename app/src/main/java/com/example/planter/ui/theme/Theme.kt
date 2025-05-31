package com.example.planter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2F4F2F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB8D1B8),
    onPrimaryContainer = Color(0xFF0A1F0A),
    secondary = Color(0xFF4A634A),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDCE8DC),
    onSecondaryContainer = Color(0xFF1A291A),
    tertiary = Color(0xFF385038),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFC8D8C8),
    onTertiaryContainer = Color(0xFF0F1F0F),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFCFDF6),
    onBackground = Color(0xFF1A1C18),
    surface = Color(0xFFFCFDF6),
    onSurface = Color(0xFF1A1C18),
    surfaceVariant = Color(0xFFE1E8E1),
    onSurfaceVariant = Color(0xFF404944),
    outline = Color(0xFF687868),
    inverseOnSurface = Color(0xFFF0F1EB),
    inverseSurface = Color(0xFF2E312F),
    inversePrimary = Color(0xFFA3C2A3),
    surfaceTint = Color(0xFF2F4F2F),
    outlineVariant = Color(0xFFBFC9C2),
    scrim = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFA3C2A3),
    onPrimary = Color(0xFF0A1F0A),
    primaryContainer = Color(0xFF2F4F2F),
    onPrimaryContainer = Color(0xFFB8D1B8),
    secondary = Color(0xFFBFCFBF),
    onSecondary = Color(0xFF1A291A),
    secondaryContainer = Color(0xFF4A634A),
    onSecondaryContainer = Color(0xFFDCE8DC),
    tertiary = Color(0xFFA8C1A8),
    onTertiary = Color(0xFF0F1F0F),
    tertiaryContainer = Color(0xFF385038),
    onTertiaryContainer = Color(0xFFC8D8C8),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color(0xFF690005),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF1A1C18),
    onBackground = Color(0xFFE2E3DC),
    surface = Color(0xFF1A1C18),
    onSurface = Color(0xFFE2E3DC),
    surfaceVariant = Color(0xFF404944),
    onSurfaceVariant = Color(0xFFBFC9C2),
    outline = Color(0xFF89938C),
    inverseOnSurface = Color(0xFF1A1C18),
    inverseSurface = Color(0xFFE2E3DC),
    inversePrimary = Color(0xFF2F4F2F),
    surfaceTint = Color(0xFFA3C2A3),
    outlineVariant = Color(0xFF404944),
    scrim = Color.Black
)

@Composable
fun PlanterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
} 