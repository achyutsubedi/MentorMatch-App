package com.example.myapplication.ui.theme

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light Mode Colors
private val LightPrimary = Color(0xFF22C55E)
private val LightOnPrimary = Color(0xFFFFFFFF)
private val LightPrimaryContainer = Color(0xFFF0FDF4)
private val LightOnPrimaryContainer = Color(0xFF0F172A)

private val LightSecondary = Color(0xFF0F172A)
private val LightOnSecondary = Color(0xFFFFFFFF)
private val LightSecondaryContainer = Color(0xFFF1F5F9)
private val LightOnSecondaryContainer = Color(0xFF0F172A)

private val LightTertiary = Color(0xFF6366F1)
private val LightOnTertiary = Color(0xFFFFFFFF)
private val LightTertiaryContainer = Color(0xFFEEF2FF)
private val LightOnTertiaryContainer = Color(0xFF312E81)

private val LightError = Color(0xFFEF4444)
private val LightOnError = Color(0xFFFFFFFF)
private val LightErrorContainer = Color(0xFFFEE2E2)
private val LightOnErrorContainer = Color(0xFF7F1D1D)

private val LightBackground = Color(0xFFFAFAFA)
private val LightOnBackground = Color(0xFF0F172A)
private val LightSurface = Color(0xFFFFFFFF)
private val LightOnSurface = Color(0xFF0F172A)

private val LightOutline = Color(0xFFE2E8F0)
private val LightOutlineVariant = Color(0xFFF1F5F9)

// Dark Mode Colors
private val DarkPrimary = Color(0xFF22C55E)
private val DarkOnPrimary = Color(0xFF0F172A)
private val DarkPrimaryContainer = Color(0xFF166534)
private val DarkOnPrimaryContainer = Color(0xFFDCFCE7)

private val DarkSecondary = Color(0xFFE2E8F0)
private val DarkOnSecondary = Color(0xFF1E293B)
private val DarkSecondaryContainer = Color(0xFF334155)
private val DarkOnSecondaryContainer = Color(0xFFF1F5F9)

private val DarkTertiary = Color(0xFF818CF8)
private val DarkOnTertiary = Color(0xFF1E1B4B)
private val DarkTertiaryContainer = Color(0xFF4338CA)
private val DarkOnTertiaryContainer = Color(0xFFE0E7FF)

private val DarkError = Color(0xFFFCA5A5)
private val DarkOnError = Color(0xFF7F1D1D)
private val DarkErrorContainer = Color(0xFFB91C1C)
private val DarkOnErrorContainer = Color(0xFFFEE2E2)

private val DarkBackground = Color(0xFF0F172A)
private val DarkOnBackground = Color(0xFFF1F5F9)
private val DarkSurface = Color(0xFF1E293B)
private val DarkOnSurface = Color(0xFFF1F5F9)

private val DarkOutline = Color(0xFF334155)
private val DarkOutlineVariant = Color(0xFF475569)

/**
 * Light Color Scheme for Mentor Match
 */
val MentorMatchLightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,

    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,

    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,

    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,

    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,

    outline = LightOutline,
    outlineVariant = LightOutlineVariant
)

/**
 * Dark Color Scheme for Mentor Match
 */
val MentorMatchDarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,

    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,

    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,

    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,

    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,

    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant
)

/**
 * Mentor Match Theme with Dynamic Dark/Light Mode Support
 *
 * @param darkTheme Whether to use dark theme (defaults to system setting)
 * @param dynamicColor Whether to use dynamic theming (Android 12+)
 */
@Composable
fun MentorMatchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> MentorMatchDarkColorScheme
        else -> MentorMatchLightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Use enableEdgeToEdge instead of deprecated statusBarColor
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


