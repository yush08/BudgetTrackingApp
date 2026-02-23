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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.viewmodel.TransactionViewModel
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
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
            .background(Color(0xFF0E0E0E))
            .padding(16.dp)
    ) {

        Text(
            text = "Statistics",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // -------- CHART CARD --------
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()),
                    color = Color.LightGray
                )

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
                    chart = lineChart(),
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            StatCard(
                title = "Income",
                amount = "+₹ ${dailyIncome.sum().toInt()}",
                color = Color(0xFF9CFF57)
            )

            StatCard(
                title = "Expenses",
                amount = "-₹ ${dailyExpense.sum().toInt()}",
                color = Color(0xFFFF7043)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Balance",
            color = Color.LightGray,
            fontSize = 14.sp
        )

        Text(
            text = "₹ ${balance.toInt()}",
            color = Color(0xFF9CFF57),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    amount: String,
    color: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        modifier = Modifier.width(160.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = Color.LightGray)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                amount,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}