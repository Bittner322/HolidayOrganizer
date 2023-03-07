package com.example.holidayorganizer.core.theme

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

@Immutable
data class OrganizerColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val backgroundSurface: Color,
    val secondaryBackground: Color,
    val dividers: Color,
    val content: Color,
    val accentGreen: Color,
    val accentRed: Color,
    val white: Color
)

val LocalOrganizerColors = staticCompositionLocalOf {
    OrganizerColors(
        primary = Color.Unspecified,
        primaryVariant = Color.Unspecified,
        secondary = Color.Unspecified,
        background = Color.Unspecified,
        backgroundSurface = Color.Unspecified,
        secondaryBackground = Color.Unspecified,
        dividers = Color.Unspecified,
        content = Color.Unspecified,
        accentGreen = Color.Unspecified,
        accentRed = Color.Unspecified,
        white = Color.White
    )
}
val LocalOrganizerTypography = staticCompositionLocalOf {
    OrganizerTypography()
}
val LocalCoinElevation = staticCompositionLocalOf {
    OrganizerElevation(
        default = 4.dp,
        pressed = 8.dp
    )
}

// Ripple

@Immutable
private object OrganizerRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = LocalContentColor.current,
        lightTheme = true
    )

    @Composable
    override fun rippleAlpha() = DefaultRippleAlpha
}

private val DefaultRippleAlpha = RippleAlpha(
    pressedAlpha = 0.08f,
    focusedAlpha = 0.08f,
    draggedAlpha = 0.12f,
    hoveredAlpha = 0.06f
)

private val DarkColorPalette = OrganizerColors(
    primary = Color(0xFF1AA5FF),
    primaryVariant = Color(0xFF141414),
    secondary = Color(0xFFF2F2F2).copy(alpha = 0.4f),
    background = Color(0xFF1F1F1F),
    backgroundSurface = Color(0xFF262626),
    secondaryBackground = Color(0xFF262626),
    dividers = Color(0xFF3D3D3D),
    content = Color(0xFFF2F2F2),
    accentGreen = Color(0xFF00BC2D),
    accentRed = Color(0xFFF23030),
    white = Color(0xFFF2F2F2)
)

private val LightColorPalette = OrganizerColors(
    primary = Color(0xFF009BFF),
    primaryVariant = Color(0xFFFFFFFF),
    secondary = Color(0xFF000000).copy(alpha = 0.4f),
    background = Color(0xFFFFFFFF),
    backgroundSurface = Color(0xFFFFFFFF),
    secondaryBackground = Color(0xFFF7F7F7),
    dividers = Color(0xFFE6E6E6),
    content = Color(0xFF000000),
    accentGreen = Color(0xFF00BC2D),
    accentRed = Color(0xFFF20000),
    white = Color(0xFFFFFFFF)
)

object OrganizerAlpha {
    const val Medium = 0.2f
    const val Soft = 0.8f
}

@Composable
fun OrganizerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val coinColors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val context = LocalContext.current
    context.updateSystemBarsConfig(LocalSystemBarsConfig.current)

    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            subtitle1 = OrganizerTheme.typography.body1,
            caption = OrganizerTheme.typography.subtitle2
        ),
        colors = MaterialTheme.colors.copy(
            background = coinColors.background,
            onBackground = coinColors.content,
            surface = OrganizerTheme.color.backgroundSurface
        )
    ) {
        CompositionLocalProvider(
            LocalContext provides context,
            LocalOrganizerColors provides coinColors,
            LocalOrganizerTypography provides OrganizerTheme.typography,
            LocalCoinElevation provides OrganizerTheme.elevation,
            LocalContentColor provides OrganizerTheme.color.content,
            LocalIndication provides rememberRipple(),
            LocalRippleTheme provides OrganizerRippleTheme,
            LocalTextStyle provides OrganizerTheme.typography.body1,
            LocalFixedInsets provides getFixedInsets(),
            content = content
        )
    }
}

object OrganizerPaddings {
    val bottomNavigationBar = 96.dp
}

object OrganizerTheme {
    val color: OrganizerColors
        @Composable
        get() = LocalOrganizerColors.current
    val typography: OrganizerTypography
        @Composable
        get() = LocalOrganizerTypography.current
    val elevation: OrganizerElevation
        @Composable
        get() = LocalCoinElevation.current
}

private fun Context.updateSystemBarsConfig(systemBarsConfig: SystemBarsConfig) {
    val isStatusBarLight = systemBarsConfig.isStatusBarLight
    val isNavigationBarLight = systemBarsConfig.isNavigationBarLight
    val window = (this as Activity).window
    val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
            Configuration.UI_MODE_NIGHT_YES
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isStatusBarLight ?: !isDarkMode
        isAppearanceLightNavigationBars = isNavigationBarLight ?: !isDarkMode
    }
}

@Composable
private fun getFixedInsets(): FixedInsets {
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()
    val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()
    return FixedInsets(
        statusBarHeight = systemBarsPadding.calculateTopPadding(),
        navigationBarsHeight = navigationBarsPadding.calculateBottomPadding()
    )
}