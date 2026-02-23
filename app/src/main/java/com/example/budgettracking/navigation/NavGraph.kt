package com.example.budgettracking.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.budgettracking.screens.*
import com.example.budgettracking.viewmodel.BudgetViewModel
import com.example.budgettracking.viewmodel.TransactionViewModel

// ---------- ROUTES ----------
const val SPLASH = "splash"
const val ONBOARDING1 = "onboarding1"
const val ONBOARDING2 = "onboarding2"
const val ONBOARDING3 = "onboarding3"
const val SIGNIN = "signin"
const val SIGNUP = "signup"
const val FORGOT = "forgot"

const val HOME = "home"
const val STATS = "stats"
const val INSIGHTS = "insights"
const val PROFILE = "profile"

const val ADD_ENTRY_ROUTER = "add_entry_router"
const val BUDGETS = "budgets"

const val ADD_BUDGET_INCOME = "add_budget_income"
const val ADD_BUDGET_DETAILS = "add_budget_details"

const val ADD_TRANSACTION = "add_transaction"

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // ✅ SINGLE INSTANCES
    val budgetViewModel: BudgetViewModel = viewModel()
    val transactionViewModel: TransactionViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = SPLASH,
        modifier = modifier
    ) {

        // ---------- SPLASH ----------
        composable(SPLASH) {
            SplashScreen {
                navController.navigate(ONBOARDING1) {
                    popUpTo(SPLASH) { inclusive = true }
                }
            }
        }

        // ---------- ONBOARDING ----------
        composable(ONBOARDING1) {
            OnboardingScreen1(
                onNextClick = { navController.navigate(ONBOARDING2) },
                onSkipClick = { navController.navigate(SIGNIN) }
            )
        }

        composable(ONBOARDING2) {
            OnboardingScreen2(
                onNextClick = { navController.navigate(ONBOARDING3) },
                onSkipClick = { navController.navigate(SIGNIN) }
            )
        }

        composable(ONBOARDING3) {
            OnboardingScreen3(
                onGetStartedClick = {
                    navController.navigate(SIGNIN) {
                        popUpTo(ONBOARDING1) { inclusive = true }
                    }
                },
                onSkipClick = { navController.navigate(SIGNIN) }
            )
        }

        // ---------- AUTH ----------
        composable(SIGNIN) {
            SignInScreen(
                onLoginClick = { navController.navigate(HOME) },
                onSignUpClick = { navController.navigate(SIGNUP) },
                onForgotPasswordClick = { navController.navigate(FORGOT) }
            )
        }

        composable(SIGNUP) {
            SignUpScreen(
                onSignUpSuccess = { navController.navigate(HOME) },
                onSignInClick = { navController.navigate(SIGNIN) }
            )
        }

        composable(FORGOT) {
            ForgotPasswordScreen {
                navController.navigate(SIGNIN)
            }
        }

        // ---------- MAIN ----------
        composable(HOME) { HomeScreen() }
        composable(STATS) {
            StatsScreen(viewModel = transactionViewModel)
        }
        composable(INSIGHTS) { InsightsScreen() }
        composable(PROFILE) { ProfileScreen() }

        // ---------- SMART ➕ ROUTER ----------
        composable(ADD_ENTRY_ROUTER) {
            LaunchedEffect(Unit) {
                if (budgetViewModel.hasBudget()) {
                    navController.navigate(BUDGETS) {
                        popUpTo(ADD_ENTRY_ROUTER) { inclusive = true }
                    }
                } else {
                    navController.navigate(ADD_BUDGET_INCOME) {
                        popUpTo(ADD_ENTRY_ROUTER) { inclusive = true }
                    }
                }
            }
        }


        // ---------- ADD BUDGET (STEP 1) ----------
        composable(ADD_BUDGET_INCOME) {
            AddBudgetIncomeScreen(
                onContinue = { income ->
                    budgetViewModel.saveIncome(income)
                    navController.navigate(ADD_BUDGET_DETAILS)
                }
            )
        }

        // ---------- ADD BUDGET (STEP 2) ----------
        composable(ADD_BUDGET_DETAILS) {
            AddBudgetDetailScreen { name, target ->
                budgetViewModel.createBudget(
                    name = name,
                    income = budgetViewModel.tempIncome,
                    target = target
                )
                navController.navigate(BUDGETS) {
                    popUpTo(ADD_BUDGET_INCOME) { inclusive = true }
                }
            }
        }

        // ---------- ADD TRANSACTION ----------
        composable(ADD_TRANSACTION) {
            AddExpenseScreen(
                viewModel = transactionViewModel,
                onBack = {
                    navController.navigate(BUDGETS) {
                        popUpTo(BUDGETS) { inclusive = true }
                    }
                }
            )
        }

        // ---------- BUDGET HUB ----------
        composable(BUDGETS) {
            BudgetsScreen(
                onAddTransactionClick = {
                    navController.navigate(ADD_ENTRY_ROUTER)
                }
            )
        }
    }
}
