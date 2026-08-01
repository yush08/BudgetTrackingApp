package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppSurface
import com.example.budgettracking.ui.theme.AppTextPrimary
import kotlin.math.roundToLong

@Composable
fun AddBudgetIncomeScreen(
    onContinue: (Long) -> Unit,
    onBack: (() -> Unit)? = null
) {
    var income by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BackTopBar(title = "Add Budget", onBack = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        // Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppSurface,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(24.dp)
        ) {

            // Progress bar
            LinearProgressIndicator(
                progress = 0.3f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = AppAccent,
                trackColor = Color(0xFF3A3A3A)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "What is your monthly\nincome?",
                color = AppTextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = money(income.toDouble()),
                color = AppAccent,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Slider(
                value = income,
                onValueChange = { income = it },
                valueRange = 0f..200000f,
                colors = SliderDefaults.colors(
                    thumbColor = AppAccent,
                    activeTrackColor = AppAccent,
                    inactiveTrackColor = Color(0xFF3A3A3A)
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onContinue(income.roundToLong())
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppAccent
            )
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}
