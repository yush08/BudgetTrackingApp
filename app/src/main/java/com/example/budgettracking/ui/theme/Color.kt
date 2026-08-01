package com.example.budgettracking.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ---------------------------------------------------------------------------
// SmartBudget palette
//
// Colors are exposed as theme-aware tokens (composable getters) backed by a
// CompositionLocal, so the SAME token names (AppBackground, AppAccent, …) used
// throughout the screens automatically resolve to the Dark or Light palette
// depending on the active theme — no per-screen changes needed to switch modes.
// ---------------------------------------------------------------------------

data class AppPalette(
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val divider: Color,
    val accent: Color,
    val accentPressed: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val income: Color,
    val expense: Color,
    val onAccent: Color,
    val isDark: Boolean,
)

val DarkPalette = AppPalette(
    background = Color(0xFF0B0B0B),
    surface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFF262626),
    divider = Color(0xFF2A2A2A),
    accent = Color(0xFFB8FF65),
    accentPressed = Color(0xFF9CE84F),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFF9E9E9E),
    textMuted = Color(0xFF6B6B6B),
    income = Color(0xFFB8FF65),
    expense = Color(0xFFFF6B6B),
    onAccent = Color(0xFF0B0B0B),
    isDark = true,
)

val LightPalette = AppPalette(
    background = Color(0xFFF4F5F7),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFEAECEF),
    divider = Color(0xFFE1E4E8),
    accent = Color(0xFF3E9422),
    accentPressed = Color(0xFF357F1D),
    textPrimary = Color(0xFF141518),
    textSecondary = Color(0xFF5B6472),
    textMuted = Color(0xFF9098A4),
    income = Color(0xFF2E9E44),
    expense = Color(0xFFD93A3A),
    onAccent = Color(0xFFFFFFFF),
    isDark = false,
)

val LocalAppPalette = staticCompositionLocalOf { DarkPalette }

// ---- Theme-aware token accessors (same names as before) ----

val AppBackground: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.background
val AppSurface: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.surface
val AppSurfaceVariant: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.surfaceVariant
val AppDivider: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.divider
val AppAccent: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.accent
val AppAccentPressed: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.accentPressed
val AppTextPrimary: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.textPrimary
val AppTextSecondary: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.textSecondary
val AppTextMuted: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.textMuted
val AppIncome: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.income
val AppExpense: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.expense
val AppOnAccent: Color
    @Composable @ReadOnlyComposable get() = LocalAppPalette.current.onAccent
