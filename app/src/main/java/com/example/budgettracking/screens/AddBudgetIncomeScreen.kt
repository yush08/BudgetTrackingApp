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
import kotlin.math.roundToLong

@Composable
fun AddBudgetIncomeScreen(
    onContinue: (Long) -> Unit
) {
    var income by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E0E0E))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Add Budget",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF555555),
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
                color = Color(0xFFB7F774),
                trackColor = Color.LightGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "What is your monthly\nincome?",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "₹ ${income.roundToLong()}",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Slider(
                value = income,
                onValueChange = { income = it },
                valueRange = 0f..200000f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Black,
                    activeTrackColor = Color(0xFFB7F774),
                    inactiveTrackColor = Color.LightGray
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
                containerColor = Color(0xFF5A74FF)
            )
        ) {
            Text(
                text = "Continue",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}
