package com.example.budgettracking.screens

import androidx.compose.runtime.Composable

@Composable
fun OnboardingScreen3(onGetStartedClick: () -> Unit, onSkipClick: () -> Unit) {
    OnboardingScaffold(
        pageIndex = 2,
        title = "Streamline Your Finances",
        body = "Track every account in one place and stay in control of your money " +
                "with real-time balances and insights.",
        primaryLabel = "Get Started",
        onPrimary = onGetStartedClick,
        onSkip = onSkipClick
    )
}
