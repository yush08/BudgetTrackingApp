package com.example.budgettracking.screens

import androidx.compose.runtime.Composable

@Composable
fun OnboardingScreen2(onNextClick: () -> Unit, onSkipClick: () -> Unit) {
    OnboardingScaffold(
        pageIndex = 1,
        title = "Budget Smarter",
        body = "Set monthly budgets, monitor your expenses, and get smart insights " +
                "to help you save more effectively.",
        primaryLabel = "Next",
        onPrimary = onNextClick,
        onSkip = onSkipClick
    )
}
