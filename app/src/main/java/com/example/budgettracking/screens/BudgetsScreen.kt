package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgettracking.R
import com.example.budgettracking.viewmodel.TransactionViewModel

@Composable
fun BudgetsScreen(
    viewModel: TransactionViewModel = viewModel(),
    onAddTransactionClick: () -> Unit
) {

    // ----------- COLLECT REAL DATA -----------
    val income by viewModel.totalIncome.collectAsState(initial = 0.0)
    val expense by viewModel.totalExpense.collectAsState(initial = 0.0)
    val balance by viewModel.balance.collectAsState(initial = 0.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ---------- HEADER ----------
        Text(
            text = "Budgets",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ---------- BUDGET SUMMARY CARD ----------
        BudgetSummaryCard(
            balance = balance,
            income = income,
            expense = expense
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ---------- ADD BUDGET CARD ----------
        AddBudgetCard()
    }
}

/* ------------------ COMPONENTS ------------------ */

@Composable
private fun BudgetSummaryCard(
    balance: Double,
    income: Double,
    expense: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF1E1E1E),
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
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹ ${balance.toInt()}",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            MiniStatCard(
                title = "Expense",
                amount = "₹ ${expense.toInt()}"
            )
            MiniStatCard(
                title = "Income",
                amount = "₹ ${income.toInt()}"
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
                color = Color(0xFF2A2A2A),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chart),
            contentDescription = null,
            tint = Color.LightGray
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                color = Color.LightGray,
                fontSize = 12.sp
            )
            Text(
                text = amount,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun AddBudgetCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                color = Color(0xFF1E1E1E),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                // TODO: navigate to Add Budget flow
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Add Budget",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "+",
                fontSize = 28.sp,
                color = Color.LightGray
            )
        }
    }
}
