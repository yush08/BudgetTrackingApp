package com.example.budgettracking.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budgettracking.screens.*

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // ✅ Splash Screen
        composable("splash") {
            SplashScreen(onFinish = {
                navController.navigate("onboarding1") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        // ✅ Onboarding
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

        composable(route = "onboarding3") {
            OnboardingScreen3(
                onGetStartedClick = {
                    navController.navigate(route = "signin") {
                        popUpTo(route = "onboarding1") { inclusive = true }
                    }
                },
                onSkipClick = {
                    navController.navigate("signin")
                }
            )
        }


        // Sign In
        composable(route = "signin") {
            SignInScreen(
                onLoginClick = { navController.navigate("home") },
                onSignUpClick = { navController.navigate("signup") },
                onForgotPasswordClick = { navController.navigate("forgot") }
            )
        }
        // ✅ Sign Up
        composable("signup") {
            SignUpScreen(
                onSignUpSuccess = { navController.navigate("home") },
                onSignInClick = { navController.navigate("signin") }
            )
        }
        // ✅ Forgot Password
        composable("forgot") {
            ForgotPasswordScreen(onResetDone = {
                navController.navigate("signin")
            })
        }
        // ✅ Home
        composable("home") {
            HomeScreen()
        }
    }
}
