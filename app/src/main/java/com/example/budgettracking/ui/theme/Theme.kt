package com.example.budgettracking.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

// Builds a Material color scheme from an AppPalette so stock Material
// components (buttons, text fields, dialogs, …) inherit the brand identity in
// both dark and light modes without per-widget overrides.
private fun schemeFor(p: AppPalette) =
    if (p.isDark) {
        darkColorScheme(
            primary = p.accent,
            onPrimary = p.onAccent,
            secondary = p.accent,
            onSecondary = p.onAccent,
            background = p.background,
            onBackground = p.textPrimary,
            surface = p.surface,
            onSurface = p.textPrimary,
            surfaceVariant = p.surfaceVariant,
            onSurfaceVariant = p.textSecondary,
            error = p.expense,
            onError = p.onAccent,
            outline = p.divider
        )
    } else {
        lightColorScheme(
            primary = p.accent,
            onPrimary = p.onAccent,
            secondary = p.accent,
            onSecondary = p.onAccent,
            background = p.background,
            onBackground = p.textPrimary,
            surface = p.surface,
            onSurface = p.textPrimary,
            surfaceVariant = p.surfaceVariant,
            onSurfaceVariant = p.textSecondary,
            error = p.expense,
            onError = p.onAccent,
            outline = p.divider
        )
    }

@Composable
fun BudgetTrackingTheme(
    darkTheme: Boolean = AppThemeState.isDark,
    content: @Composable () -> Unit
) {
    val palette = if (darkTheme) DarkPalette else LightPalette

    CompositionLocalProvider(LocalAppPalette provides palette) {
        MaterialTheme(
            colorScheme = schemeFor(palette),
            typography = Typography
        ) {
            // Make Outfit the ambient default so any Text without an explicit
            // fontFamily still renders in the brand typeface.
            ProvideTextStyle(value = Typography.bodyLarge, content = content)
        }
    }
}
