package com.example.sampleapp.core.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = White,
    onPrimary = Neon01,
    primaryContainer = Graphite,
    onPrimaryContainer = White,
    inversePrimary = Green03,
    secondary = Green04,
    onSecondary = Green01,
    secondaryContainer = Green04,
    onSecondaryContainer = White,
    tertiary = Yellow05,
    onTertiary = Yellow01,
    tertiaryContainer = Yellow04,
    onTertiaryContainer = White,
    error = Red02,
    onError = Red05,
    errorContainer = Red04,
    onErrorContainer = Red01,
    surface = Graphite,
    onSurface = White,
    inverseSurface = Neon05,
    inverseOnSurface = Black,
    outline = DarkGray,
    outlineVariant = Cosmos,
    scrim = Black,
)

private val LightColorScheme = lightColorScheme(
    primary = Neon01,
    onPrimary = White,
    primaryContainer = White,
    onPrimaryContainer = Black,
    inversePrimary = Neon01,
    secondary = Green04,
    onSecondary = White,
    secondaryContainer = Green01,
    onSecondaryContainer = Green04,
    tertiary = Yellow01,
    onTertiary = Black,
    tertiaryContainer = Yellow03A40,
    onTertiaryContainer = Yellow04,
    error = Red03,
    onError = White,
    errorContainer = Red01,
    onErrorContainer = Red06,
    surface = PaperGray,
    onSurface = DuskGray,
    inverseSurface = Yellow05,
    inverseOnSurface = White,
    outline = LightGray,
    outlineVariant = DarkGray,
    scrim = Black,
)

private val LightColors = CustomColorsPalette(
    primary = md_theme_light_primary40,
    onPrimary = md_theme_light_on_primary100,
    secondary = md_theme_light_secondary40,
    onSecondary = md_theme_light_on_secondary100,
    tertiary = md_theme_light_tertiary40,
    onTertiary = md_theme_light_on_tertiary100,
    error = md_theme_light_error40,
    onError = md_theme_light_on_error100,
    primaryContainer = md_theme_light_primary_container90,
    onPrimaryContainer = md_theme_light_on_primary_container10,
    secondaryContainer = md_theme_light_secondary_container90,
    onSecondaryContainer = md_theme_light_on_secondary_container10,
    tertiaryContainer = md_theme_light_tertiary_container90,
    onTertiaryContainer = md_theme_light_on_tertiary_container10,
    errorContainer = md_theme_light_error_container90,
    onErrorContainer = md_theme_light_on_error_container10,
    primaryFixed = md_theme_light_primary_fixed90,
    primaryFixedDim = md_theme_light_primary_fixed_dim80,
    onPrimaryFixed = md_theme_light_on_primary_fixed10,
    onPrimaryFixedVariant = md_theme_light_on_primary_fixed_variant30,
    secondaryFixed = md_theme_light_secondary_fixed90,
    secondaryFixedDim = md_theme_light_secondary_fixed_dim80,
    onSecondaryFixed = md_theme_light_on_secondary_fixed10,
    onSecondaryFixedVariant = md_theme_light_on_secondary_fixed_variant30,
    tertiaryFixed = md_theme_light_tertiary_fixed90,
    tertiaryFixedDim = md_theme_light_tertiary_fixed_dim80,
    onTertiaryFixed = md_theme_light_on_tertiary_fixed10,
    onTertiaryFixedVariant = md_theme_light_on_tertiary_fixed_variant30,
    surface = md_theme_light_surface98,
    onSurface = md_theme_light_on_surface10,
    surfaceVariant = md_theme_light_surface_variant90,
    onSurfaceVariant = md_theme_light_on_surface_variant30,
    surfaceContainerLowest = md_theme_light_surface_container_lowest100,
    surfaceContainerLow = md_theme_light_surface_container_low96,
    surfaceContainer = md_theme_light_surface_container94,
    surfaceContainerHigh = md_theme_light_surface_container_high92,
    surfaceContainerHighest = md_theme_light_surface_container_highest90,
    outline = md_theme_light_outline50,
    outlineVariant = md_theme_light_outline_variant80,
    hallA = md_theme_light_hallA,
    hallB = md_theme_light_hallB,
    hallC = md_theme_light_hallC,
    hallD = md_theme_light_hallD,
    hallE = md_theme_light_hallE,
    outlineVariantHallText = md_theme_light_outline_variant_hallText
)

private val DarkColors = CustomColorsPalette(
    primary = md_theme_dark_primary80,
    onPrimary = md_theme_dark_on_primary20,
    secondary = md_theme_dark_secondary80,
    onSecondary = md_theme_dark_on_secondary20,
    tertiary = md_theme_dark_tertiary80,
    onTertiary = md_theme_dark_on_tertiary20,
    error = md_theme_dark_error80,
    onError = md_theme_dark_on_error20,
    primaryContainer = md_theme_dark_primary_container30,
    onPrimaryContainer = md_theme_dark_on_primary_container90,
    secondaryContainer = md_theme_dark_secondary_container30,
    onSecondaryContainer = md_theme_dark_on_secondary_container90,
    tertiaryContainer = md_theme_dark_tertiary_container30,
    onTertiaryContainer = md_theme_dark_on_tertiary_container90,
    errorContainer = md_theme_dark_error_container30,
    onErrorContainer = md_theme_dark_on_error_container90,
    primaryFixed = md_theme_dark_primary_fixed90,
    primaryFixedDim = md_theme_dark_primary_fixed_dim80,
    onPrimaryFixed = md_theme_dark_on_primary_fixed10,
    onPrimaryFixedVariant = md_theme_dark_on_primary_fixed_variant30,
    secondaryFixed = md_theme_dark_secondary_fixed90,
    secondaryFixedDim = md_theme_dark_secondary_fixed_dim80,
    onSecondaryFixed = md_theme_dark_on_secondary_fixed10,
    onSecondaryFixedVariant = md_theme_dark_on_secondary_fixed_variant30,
    tertiaryFixed = md_theme_dark_tertiary_fixed90,
    tertiaryFixedDim = md_theme_dark_tertiary_fixed_dim80,
    onTertiaryFixed = md_theme_dark_on_tertiary_fixed10,
    onTertiaryFixedVariant = md_theme_dark_on_tertiary_fixed_variant30,
    surface = md_theme_dark_surface6,
    onSurface = md_theme_dark_on_surface80,
    surfaceVariant = md_theme_dark_surface_variant30,
    onSurfaceVariant = md_theme_dark_on_surface_variant80,
    surfaceContainerLowest = md_theme_dark_surface_container_lowest4,
    surfaceContainerLow = md_theme_dark_surface_container_low10,
    surfaceContainer = md_theme_dark_surface_container12,
    surfaceContainerHigh = md_theme_dark_surface_container_high17,
    surfaceContainerHighest = md_theme_dark_surface_container_highest22,
    outline = md_theme_dark_outline60,
    outlineVariant = md_theme_dark_outline_variant30,
    hallA = md_theme_dark_hallA,
    hallB = md_theme_dark_hallB,
    hallC = md_theme_dark_hallC,
    hallD = md_theme_dark_hallD,
    hallE = md_theme_dark_hallE,
    outlineVariantHallText = md_theme_dark_outline_variant_hallText
)

val LocalDarkTheme = compositionLocalOf { true }

val ColorScheme.surfaceDim
    @Composable
    get() = if (LocalDarkTheme.current) Black else PaleGray

@Composable
fun KnightsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    if (!LocalInspectionMode.current) {
        val view = LocalView.current
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalDarkTheme provides darkTheme,
        LocalTypography provides Typography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

object KnightsTheme {
    val typography: KnightsTypography
        @Composable
        get() = LocalTypography.current
}

@Composable
fun CustomMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    if (!LocalInspectionMode.current) {
        val view = LocalView.current
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalDarkTheme provides darkTheme,
        LocalTypography provides Typography
    ) {
        CustomMaterialTheme(
            darkTheme = darkTheme,
            content = content,
        )
    }
}

object CustomMaterialTheme {
    val typography: KnightsTypography
        @Composable
        get() = LocalTypography.current
}