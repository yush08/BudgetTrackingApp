package com.example.budgettracking.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budgettracking.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {

        composable("splash") {
            SplashScreen {
                navController.navigate("onboarding1") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("onboarding1") {
            OnboardingScreen1(
                onNextClick = { navController.navigate("onboarding2") },
                onSkipClick = { navController.navigate("signin") }
            )
        }

        composable("onboarding2") {
            OnboardingScreen2(
                onNextClick = { navController.navigate("onboarding3") },
                onSkipClick = { navController.navigate("signin") }
            )
        }

        composable("onboarding3") {
            OnboardingScreen3(
                onGetStartedClick = {
                    navController.navigate("signin") {
                        popUpTo("onboarding1") { inclusive = true }
                    }
                },
                onSkipClick = { navController.navigate("signin") }
            )
        }

        composable("signin") {
            SignInScreen(
                onLoginClick = { navController.navigate("home") },
                onSignUpClick = { navController.navigate("signup") },
                onForgotPasswordClick = { navController.navigate("forgot") }
            )
        }

        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = { navController.navigate("home") },
                onSignInClick = { navController.navigate("signin") }
            )
        }

        composable("forgot") {
            ForgotPasswordScreen {
                navController.navigate("signin")
            }
        }

        composable(route = "home") {
            HomeScreen(
                onAddTransactionClick = {
                    navController.navigate("add_transaction")
                }
            )
        }


        composable("stats") {
            StatsScreen()
        }

        composable("insights") {
            InsightsScreen()
        }

        composable("profile") {
            ProfileScreen()
        }

        composable("add_transaction") {
            AddExpenseScreen()
        }
    }
}
