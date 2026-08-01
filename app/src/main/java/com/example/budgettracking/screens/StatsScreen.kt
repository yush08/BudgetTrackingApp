package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppExpense
import com.example.budgettracking.ui.theme.AppSurface
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
import com.example.budgettracking.viewmodel.TransactionViewModel
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.core.entry.entryModelOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatsScreen(
    viewModel: TransactionViewModel = viewModel()
) {

    val transactions by viewModel.transactions.collectAsState()

    val calendar = Calendar.getInstance()

    // -------- GROUP DAILY DATA --------
    val dailyIncome = MutableList(7) { 0.0 }
    val dailyExpense = MutableList(7) { 0.0 }

    transactions.forEach { transaction ->
        calendar.timeInMillis = transaction.timestamp
        val dayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1

        if (transaction.type == TransactionType.INCOME)
            dailyIncome[dayIndex] += transaction.amount
        else
            dailyExpense[dayIndex] += transaction.amount
    }

    val balance = dailyIncome.sum() - dailyExpense.sum()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(16.dp)
    ) {

        Text(
            text = "Statistics",
            color = AppTextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // -------- CHART CARD --------
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = AppSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()),
                        color = AppTextSecondary
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        LegendDot("Income", AppAccent)
                        LegendDot("Expense", AppExpense)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                val incomeEntries = dailyIncome.mapIndexed { index, value ->
                    com.patrykandpatrick.vico.core.entry.FloatEntry(
                        index.toFloat(),
                        value.toFloat()
                    )
                }

                val expenseEntries = dailyExpense.mapIndexed { index, value ->
                    com.patrykandpatrick.vico.core.entry.FloatEntry(
                        index.toFloat(),
                        value.toFloat()
                    )
                }

                Chart(
                    chart = lineChart(
                        lines = listOf(
                            lineSpec(lineColor = AppAccent),
                            lineSpec(lineColor = AppExpense)
                        )
                    ),
                    model = entryModelOf(
                        incomeEntries,
                        expenseEntries
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // -------- SUMMARY CARDS --------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            StatCard(
                title = "Income",
                amount = money(dailyIncome.sum()),
                color = AppAccent,
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "Expenses",
                amount = money(dailyExpense.sum()),
                color = AppExpense,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Balance",
            color = AppTextSecondary,
            fontSize = 14.sp
        )

        Text(
            text = money(balance),
            color = AppAccent,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LegendDot(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(9.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, color = AppTextSecondary, fontSize = 12.sp)
    }
}

@Composable
private fun StatCard(
    title: String,
    amount: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = AppTextSecondary)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                amount,
                color = color,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}