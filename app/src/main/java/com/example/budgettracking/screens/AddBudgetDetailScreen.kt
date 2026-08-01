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
import com.example.budgettracking.ui.theme.AppSurfaceVariant
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary


@Composable
fun AddBudgetDetailScreen(
    onFinish: (String, Long) -> Unit,
    onBack: (() -> Unit)? = null
) {
    var budgetName by remember { mutableStateOf("") }
    var targetAmount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BackTopBar(title = "Add Budget", onBack = onBack)

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppSurface,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(24.dp)
        ) {

            // Progress (step 2)
            LinearProgressIndicator(
                progress = 0.6f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = AppAccent,
                trackColor = Color(0xFF3A3A3A)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Let's create a new\nExpense budget",
                color = AppTextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "BUDGET NAME",
                color = AppTextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = budgetName,
                onValueChange = { budgetName = it },
                placeholder = { Text("Enter Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppSurfaceVariant,
                    unfocusedContainerColor = AppSurfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = AppAccent,
                    focusedTextColor = AppTextPrimary,
                    unfocusedTextColor = AppTextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "BUDGET TARGET",
                color = AppTextSecondary,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = targetAmount,
                onValueChange = { targetAmount = it },
                placeholder = { Text("₹0") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppSurfaceVariant,
                    unfocusedContainerColor = AppSurfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = AppAccent,
                    focusedTextColor = AppTextPrimary,
                    unfocusedTextColor = AppTextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val target = targetAmount.toLongOrNull() ?: 0L
                onFinish(budgetName, target)
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

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = {
                val target = targetAmount.toLongOrNull() ?: 0L
                onFinish(budgetName, target)
            }
        ) {
            Text("Skip", color = AppTextSecondary)
        }
    }
}
