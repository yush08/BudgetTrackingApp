package com.example.budgettracking.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.budgettracking.navigation.HOME
import com.example.budgettracking.navigation.INSIGHTS
import com.example.budgettracking.navigation.PROFILE
import com.example.budgettracking.navigation.STATS

@Composable
fun BottomNavBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val navColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color(0xFFB7F774),
        unselectedIconColor = Color(0xFF9A9A9A),
        indicatorColor = Color.Transparent
    )

    NavigationBar(
        modifier = Modifier.height(64.dp),
        containerColor = Color(0xFF222222),
        tonalElevation = 0.dp
    ) {

        // HOME
        NavigationBarItem(
            selected = currentRoute == HOME,
            onClick = {
                navController.navigate(HOME) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            colors = navColors
        )

        // STATS
        NavigationBarItem(
            selected = currentRoute == STATS,
            onClick = {
                navController.navigate(STATS) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.BarChart, contentDescription = "Stats") },
            colors = navColors
        )

        Spacer(modifier = Modifier.weight(1f))

        // INSIGHTS
        NavigationBarItem(
            selected = currentRoute == INSIGHTS,
            onClick = {
                navController.navigate(INSIGHTS) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Insights") },
            colors = navColors
        )

        // PROFILE
        NavigationBarItem(
            selected = currentRoute == PROFILE,
            onClick = {
                navController.navigate(PROFILE) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.GridView, contentDescription = "Profile") },
            colors = navColors
        )
    }
}