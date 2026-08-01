package com.example.budgettracking.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * App-wide theme state. Kept as a simple observable singleton so any screen can
 * flip between dark and light and the whole UI recomposes. Defaults to dark.
 */
object AppThemeState {
    var isDark by mutableStateOf(true)

    fun toggle() {
        isDark = !isDark
    }
}
