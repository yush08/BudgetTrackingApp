package com.example.budgettracking.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracking.R
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.Outfit

/**
 * Shared onboarding layout so the three onboarding steps stay pixel-consistent.
 * The middle content scrolls, which keeps the fixed logo / bottom actions from
 * overflowing on short screens.
 */
@Composable
fun OnboardingScaffold(
    pageIndex: Int,              // 0-based
    title: String,
    body: String,
    primaryLabel: String,
    onPrimary: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 32.dp, vertical = 48.dp)
    ) {

        // Brand
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "SmartBudget",
                color = AppAccent,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Outfit
            )
        }

        // Center content (scrollable so it never clips)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = AppAccent,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Outfit,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = body,
                color = AppTextPrimary.copy(alpha = 0.85f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Outfit,
                lineHeight = 21.sp,
                textAlign = TextAlign.Center
            )
        }

        // Page indicator
        PageDots(current = pageIndex, total = 3)

        Spacer(modifier = Modifier.height(24.dp))

        // Primary action
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(AppAccent)
                .clickable { onPrimary() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = primaryLabel,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Outfit
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Skip",
            color = AppTextPrimary.copy(alpha = 0.75f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Outfit,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { onSkip() }
        )
    }
}

@Composable
private fun PageDots(current: Int, total: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(total) { index ->
            val active = index == current
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(8.dp)
                    .width(if (active) 22.dp else 8.dp)
                    .clip(CircleShape)
                    .background(if (active) AppAccent else AppTextPrimary.copy(alpha = 0.25f))
            )
        }
    }
}
