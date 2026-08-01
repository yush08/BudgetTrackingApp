package com.example.budgettracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.budgettracking.ui.MainScaffold
import com.example.budgettracking.ui.theme.AppThemeState
import com.example.budgettracking.ui.theme.BudgetTrackingTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            // Reading AppThemeState.isDark here makes the whole app recompose
            // when the user toggles between dark and light.
            BudgetTrackingTheme(darkTheme = AppThemeState.isDark) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScaffold()
                }
            }
        }
    }
}
