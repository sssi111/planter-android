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
    primary = Color(0xFF2F4F2F), // Pine green
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB8D1B8), // Light pine green
    onPrimaryContainer = Color(0xFF0A1F0A), // Very dark pine green
    secondary = Color(0xFF4A634A), // Darker pine green
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDCE8DC), // Very light pine green
    onSecondaryContainer = Color(0xFF1A291A), // Dark pine green
    tertiary = Color(0xFF385038), // Deep pine green
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFC8D8C8), // Muted pine green
    onTertiaryContainer = Color(0xFF0F1F0F), // Very dark pine green
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFCFDF6),
    onBackground = Color(0xFF1A1C18),
    surface = Color(0xFFFCFDF6),
    onSurface = Color(0xFF1A1C18),
    surfaceVariant = Color(0xFFE1E8E1), // Light pine green variant
    onSurfaceVariant = Color(0xFF404944),
    outline = Color(0xFF687868), // Muted pine green
    inverseOnSurface = Color(0xFFF0F1EB),
    inverseSurface = Color(0xFF2E312F),
    inversePrimary = Color(0xFFA3C2A3), // Light pine green
    surfaceTint = Color(0xFF2F4F2F), // Pine green
    outlineVariant = Color(0xFFBFC9C2),
    scrim = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFA3C2A3), // Light pine green
    onPrimary = Color(0xFF0A1F0A), // Very dark pine green
    primaryContainer = Color(0xFF2F4F2F), // Pine green
    onPrimaryContainer = Color(0xFFB8D1B8), // Light pine green
    secondary = Color(0xFFBFCFBF), // Light pine green
    onSecondary = Color(0xFF1A291A), // Dark pine green
    secondaryContainer = Color(0xFF4A634A), // Darker pine green
    onSecondaryContainer = Color(0xFFDCE8DC), // Very light pine green
    tertiary = Color(0xFFA8C1A8), // Light pine green
    onTertiary = Color(0xFF0F1F0F), // Very dark pine green
    tertiaryContainer = Color(0xFF385038), // Deep pine green
    onTertiaryContainer = Color(0xFFC8D8C8), // Muted pine green
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color(0xFF690005),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF1A1C18),
    onBackground = Color(0xFFE2E3DC),
    surface = Color(0xFF1A1C18),
    onSurface = Color(0xFFE2E3DC),
    surfaceVariant = Color(0xFF404944), // Dark pine green variant
    onSurfaceVariant = Color(0xFFBFC9C2),
    outline = Color(0xFF89938C), // Muted pine green
    inverseOnSurface = Color(0xFF1A1C18),
    inverseSurface = Color(0xFFE2E3DC),
    inversePrimary = Color(0xFF2F4F2F), // Pine green
    surfaceTint = Color(0xFFA3C2A3), // Light pine green
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