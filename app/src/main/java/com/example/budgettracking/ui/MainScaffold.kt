package com.example.budgettracking.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.budgettracking.navigation.*
import com.example.budgettracking.ui.components.BottomNavBar
import com.example.budgettracking.ui.components.FloatingAddButton
import com.example.budgettracking.ui.components.FloatingAddExpenseButton

@Composable
fun MainScaffold() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        HOME, STATS, INSIGHTS, PROFILE, BUDGETS
    )

    // Right FAB visible only on Budgets
    val showRightFab = currentRoute == BUDGETS

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                Box {
                    BottomNavBar(navController)

                    // CENTER FAB
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-28).dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        FloatingAddButton {
                            navController.navigate(ADD_ENTRY_ROUTER) {
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {

            NavGraph(navController = navController)

            // RIGHT-BOTTOM FAB
            if (showRightFab) {
                FloatingAddExpenseButton(
                    onClick = {
                        navController.navigate(ADD_TRANSACTION) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
