package com.example.budgettracking.screens

import androidx.compose.runtime.Composable

@Composable
fun OnboardingScreen1(onNextClick: () -> Unit, onSkipClick: () -> Unit) {
    OnboardingScaffold(
        pageIndex = 0,
        title = "Take Charge of Your Money",
        body = "Welcome to SmartBudget, your intelligent finance companion. " +
                "Get a clear view of your income, spending, and savings all in one smart place.",
        primaryLabel = "Next",
        onPrimary = onNextClick,
        onSkip = onSkipClick
    )
}
