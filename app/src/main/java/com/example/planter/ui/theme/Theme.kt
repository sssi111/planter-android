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
    primary = Color(0xFF2E7D32),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFA5D6A7),
    onPrimaryContainer = Color(0xFF002105),
    secondary = Color(0xFF55624C),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD8E7CB),
    onSecondaryContainer = Color(0xFF131F0D),
    tertiary = Color(0xFF386667),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFBCEBEC),
    onTertiaryContainer = Color(0xFF002021),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFFCFDF6),
    onBackground = Color(0xFF1A1C18),
    surface = Color(0xFFFCFDF6),
    onSurface = Color(0xFF1A1C18),
    surfaceVariant = Color(0xFFDCE5DD),
    onSurfaceVariant = Color(0xFF404944),
    outline = Color(0xFF707973),
    inverseOnSurface = Color(0xFFF0F1EB),
    inverseSurface = Color(0xFF2E312F),
    inversePrimary = Color(0xFF89B98A),
    surfaceTint = Color(0xFF2E7D32),
    outlineVariant = Color(0xFFBFC9C2),
    scrim = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF89B98A),
    onPrimary = Color(0xFF00390D),
    primaryContainer = Color(0xFF005314),
    onPrimaryContainer = Color(0xFFA5D6A7),
    secondary = Color(0xFFBCCBB0),
    onSecondary = Color(0xFF253420),
    secondaryContainer = Color(0xFF3B4A35),
    onSecondaryContainer = Color(0xFFD8E7CB),
    tertiary = Color(0xFFA0CFCF),
    onTertiary = Color(0xFF003738),
    tertiaryContainer = Color(0xFF1E4E4F),
    onTertiaryContainer = Color(0xFFBCEBEC),
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
    inversePrimary = Color(0xFF2E7D32),
    surfaceTint = Color(0xFF89B98A),
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