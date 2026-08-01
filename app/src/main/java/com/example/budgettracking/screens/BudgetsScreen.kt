package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgettracking.R
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppExpense
import com.example.budgettracking.ui.theme.AppSurface
import com.example.budgettracking.ui.theme.AppSurfaceVariant
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
import com.example.budgettracking.viewmodel.TransactionViewModel

@Composable
fun BudgetsScreen(
    viewModel: TransactionViewModel = viewModel(),
    onAddTransactionClick: () -> Unit,
    onAddBudgetClick: () -> Unit = {},
    onDetailsClick: () -> Unit = {}
) {

    // ----------- COLLECT REAL DATA -----------
    val income by viewModel.totalIncome.collectAsState(initial = 0.0)
    val expense by viewModel.totalExpense.collectAsState(initial = 0.0)
    val balance by viewModel.balance.collectAsState(initial = 0.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // ---------- HEADER ----------
        Text(
            text = "Budgets",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = AppTextPrimary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ---------- BUDGET SUMMARY CARD ----------
        BudgetSummaryCard(
            balance = balance,
            income = income,
            expense = expense,
            onDetailsClick = onDetailsClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- MONTHLY BUDGET PROGRESS ----------
        MonthlyBudgetCard(income = income, expense = expense)

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- ADD BUDGET CARD ----------
        AddBudgetCard(onClick = onAddBudgetClick)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

/* ------------------ COMPONENTS ------------------ */

@Composable
private fun BudgetSummaryCard(
    balance: Double,
    income: Double,
    expense: Double,
    onDetailsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = AppSurface,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Total Balance",
                    color = AppTextSecondary,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = money(balance),
                    color = AppTextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(onClick = onDetailsClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "View statistics",
                    tint = AppAccent
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            MiniStatCard(
                title = "Expense",
                amount = money(expense)
            )
            MiniStatCard(
                title = "Income",
                amount = money(income)
            )
        }
    }
}

@Composable
private fun MiniStatCard(
    title: String,
    amount: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                color = AppSurfaceVariant,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chart),
            contentDescription = null,
            tint = AppTextSecondary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                color = AppTextSecondary,
                fontSize = 12.sp
            )
            Text(
                text = amount,
                color = AppTextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun MonthlyBudgetCard(income: Double, expense: Double) {
    // Use income as the month's budget baseline (fall back to a sample target
    // so the card is meaningful before any income is recorded).
    val budget = if (income > 0) income else 50000.0
    val fraction = (expense / budget).toFloat().coerceIn(0f, 1f)
    val remaining = (budget - expense).coerceAtLeast(0.0)
    val overBudget = expense > budget
    val barColor = when {
        overBudget -> AppExpense
        fraction > 0.8f -> Color(0xFFF5A524)
        else -> AppAccent
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppSurface, RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Monthly Budget", color = AppTextPrimary, fontWeight = FontWeight.SemiBold)
            Text("${(fraction * 100).toInt()}% used", color = barColor, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = fraction,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = barColor,
            trackColor = AppSurfaceVariant
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Spent ${money(expense)} of ${money(budget)}", color = AppTextSecondary, fontSize = 12.sp)
            Text(
                if (overBudget) "Over by ${money(expense - budget)}" else "${money(remaining)} left",
                color = if (overBudget) AppExpense else AppAccent,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun AddBudgetCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                color = AppSurface,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Add Budget",
                color = AppTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "+",
                fontSize = 28.sp,
                color = AppTextSecondary
            )
        }
    }
}
