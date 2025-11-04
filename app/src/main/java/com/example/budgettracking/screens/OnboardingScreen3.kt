package com.example.budgettracking.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracking.R


@Composable
fun OnboardingScreen3(onGetStartedClick: () -> Unit, onSkipClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0B0B))
            .padding(50.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )

            Text(
                text = "SmartBudget",
                color = Color(0xFFB8FF65),
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Outfit
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Streamline Your Finances",
                color = Color(0xFFB8FF65),
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Outfit
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Connect your bank accounts and cards to get real-time balance updates and stay in control of your finances.",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = Outfit,
                lineHeight = 20.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        Color(0xFFB8FF65),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .clickable { onGetStartedClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Get Started",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Outfit
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Skip",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Outfit,
                modifier = Modifier.clickable { onSkipClick() }
            )
        }
    }
}
