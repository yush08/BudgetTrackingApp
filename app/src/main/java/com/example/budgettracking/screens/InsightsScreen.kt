package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppExpense
import com.example.budgettracking.ui.theme.AppSurface
import com.example.budgettracking.ui.theme.AppSurfaceVariant
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
import com.example.budgettracking.viewmodel.TransactionViewModel
import kotlin.math.roundToInt

@Composable
fun InsightsScreen(
    viewModel: TransactionViewModel = viewModel()
) {
    val transactions by viewModel.transactions.collectAsState()

    val income = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val savings = (income - expense).coerceAtLeast(0.0)
    val savingsRate = if (income > 0) (savings / income * 100).roundToInt() else 0

    // Group expenses by title to build a spending breakdown.
    val topSpending = transactions
        .filter { it.type == TransactionType.EXPENSE }
        .groupBy { it.title }
        .map { (title, list) -> title to list.sumOf { it.amount } }
        .sortedByDescending { it.second }
        .take(5)
    val maxSpend = topSpending.maxOfOrNull { it.second } ?: 1.0

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Insights",
                color = AppTextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InsightStat("Spent", money(expense), Icons.Default.TrendingDown, AppExpense, Modifier.weight(1f))
                InsightStat("Saved", money(savings), Icons.Default.TrendingUp, AppAccent, Modifier.weight(1f))
            }
        }

        item { SavingsRateCard(savingsRate) }

        item {
            Text(
                "Top Spending",
                color = AppTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        if (topSpending.isEmpty()) {
            item {
                Text("No expenses to analyse yet.", color = AppTextSecondary, fontSize = 13.sp)
            }
        } else {
            items(topSpending.size) { i ->
                val (title, amount) = topSpending[i]
                SpendingBar(title, amount, (amount / maxSpend).toFloat())
            }
        }

        item { Spacer(Modifier.height(4.dp)) }

        item {
            Text("Smart Tips", color = AppTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        item {
            TipCard(
                if (savingsRate >= 20)
                    "Great job! You're saving $savingsRate% of your income. Consider moving it to a savings goal."
                else
                    "Your savings rate is $savingsRate%. Aim for at least 20% by trimming your top expense category."
            )
        }
        item {
            TipCard("Review recurring subscriptions monthly — small charges add up over time.")
        }
    }
}

@Composable
private fun InsightStat(
    label: String,
    amount: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, null, tint = accent, modifier = Modifier.size(22.dp))
            Text(amount, color = AppTextPrimary, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(label, color = AppTextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SavingsRateCard(rate: Int) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text("Savings Rate", color = AppTextSecondary, fontSize = 13.sp)
            Spacer(Modifier.height(6.dp))
            Text("$rate%", color = AppAccent, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(AppSurfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth((rate / 100f).coerceIn(0f, 1f))
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(AppAccent)
                )
            }
        }
    }
}

@Composable
private fun SpendingBar(title: String, amount: Double, fraction: Float) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = AppTextPrimary, fontSize = 14.sp)
            Text(money(amount), color = AppTextSecondary, fontSize = 13.sp)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(AppSurfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction.coerceIn(0.02f, 1f))
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(AppAccent)
            )
        }
    }
}

@Composable
private fun TipCard(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppSurface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Lightbulb, null, tint = AppAccent, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(12.dp))
        Text(text, color = AppTextSecondary, fontSize = 13.sp, lineHeight = 18.sp)
    }
}
