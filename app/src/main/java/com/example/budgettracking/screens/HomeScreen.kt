package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.budgettracking.data.model.Transaction
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.ui.theme.AppAccent
import com.example.budgettracking.ui.theme.AppBackground
import com.example.budgettracking.ui.theme.AppExpense
import com.example.budgettracking.ui.theme.AppSurface
import com.example.budgettracking.ui.theme.AppSurfaceVariant
import com.example.budgettracking.ui.theme.AppTextMuted
import com.example.budgettracking.ui.theme.AppTextPrimary
import com.example.budgettracking.ui.theme.AppTextSecondary
import com.example.budgettracking.ui.theme.AppThemeState
import com.example.budgettracking.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onSeeAllClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {}
) {
    val transactions by viewModel.transactions.collectAsState()

    val income = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val balance = income - expense

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { HeaderSection(balance, onProfileClick, onNotificationsClick) }
        item { SavingsCard(income, expense) }
        item { AccountSummaryRow(income, expense, balance) }
        item {
            TransactionHistorySection(
                transactions = transactions,
                onSeeAllClick = onSeeAllClick,
                onDelete = { viewModel.removeTransaction(it) }
            )
        }
    }
}

/* ---------------------------------- HEADER --------------------------------- */

@Composable
private fun HeaderSection(
    balance: Double,
    onProfileClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onProfileClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = AppAccent,
                    modifier = Modifier.size(38.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text("Welcome back", color = AppTextSecondary, fontSize = 12.sp)
                    Text("Alex Morgan", color = AppTextPrimary, fontWeight = FontWeight.SemiBold)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { AppThemeState.toggle() }) {
                    Icon(
                        imageVector = if (AppThemeState.isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle theme",
                        tint = AppTextPrimary
                    )
                }
                IconButton(onClick = onNotificationsClick) {
                    Icon(Icons.Default.NotificationsNone, "Notifications", tint = AppTextPrimary)
                }
            }
        }

        Text("Total Balance", color = AppTextSecondary)
        Text(
            text = money(balance),
            color = AppAccent,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/* ------------------------------- SAVINGS CARD ------------------------------ */

@Composable
private fun SavingsCard(income: Double, expense: Double) {
    val savings = (income - expense).coerceAtLeast(0.0)
    val rate = if (income > 0) (savings / income * 100).roundToInt() else 0

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Well done!", color = AppTextPrimary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "You saved $rate% of your income this month.",
                    color = AppTextSecondary,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = (rate / 100f).coerceIn(0f, 1f),
                    color = AppAccent,
                    strokeWidth = 6.dp,
                    modifier = Modifier.size(72.dp)
                )
                Text("$rate%", color = AppTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

/* ----------------------------- ACCOUNT SUMMARY ----------------------------- */

@Composable
private fun AccountSummaryRow(income: Double, expense: Double, balance: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard("Income", money(income), Icons.Default.SouthWest, AppAccent, Modifier.weight(1f))
        SummaryCard("Expense", money(expense), Icons.Default.NorthEast, AppExpense, Modifier.weight(1f))
        SummaryCard("Savings", money(balance.coerceAtLeast(0.0)), Icons.Default.Savings, AppTextPrimary, Modifier.weight(1f))
    }
}

@Composable
private fun SummaryCard(
    title: String,
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
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(icon, null, tint = accent, modifier = Modifier.size(20.dp))
            Text(amount, color = AppTextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
            Text(title, color = AppTextSecondary, fontSize = 12.sp)
        }
    }
}

/* --------------------------- TRANSACTION HISTORY --------------------------- */

@Composable
private fun TransactionHistorySection(
    transactions: List<Transaction>,
    onSeeAllClick: () -> Unit,
    onDelete: (Transaction) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Transaction History", color = AppTextPrimary, fontWeight = FontWeight.Bold)
            Text(
                "See All",
                color = AppAccent,
                fontSize = 13.sp,
                modifier = Modifier.clickable { onSeeAllClick() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (transactions.isEmpty()) {
            EmptyTransactions()
        } else {
            transactions.take(6).forEach { transaction ->
                TransactionRow(transaction, onDelete = { onDelete(transaction) })
            }
        }
    }
}

@Composable
private fun EmptyTransactions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.ReceiptLong, null, tint = AppTextSecondary, modifier = Modifier.size(40.dp))
        Spacer(Modifier.height(8.dp))
        Text("No transactions yet", color = AppTextSecondary)
        Text("Add one with the + button", color = AppTextMuted, fontSize = 12.sp)
    }
}

@Composable
private fun TransactionRow(
    transaction: Transaction,
    onDelete: () -> Unit
) {
    val isExpense = transaction.type == TransactionType.EXPENSE
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AppSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (isExpense) Icons.Default.NorthEast else Icons.Default.SouthWest,
                    null,
                    tint = if (isExpense) AppExpense else AppAccent,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(transaction.title, color = AppTextPrimary, maxLines = 1)
                Text(
                    transaction.note.ifBlank { "No description" },
                    color = AppTextSecondary,
                    fontSize = 12.sp,
                    maxLines = 1
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = (if (isExpense) "- " else "+ ") + money(transaction.amount),
                color = if (isExpense) AppExpense else AppAccent,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                formatTime(transaction.timestamp),
                color = AppTextSecondary,
                fontSize = 11.sp
            )
        }

        IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
            Icon(
                Icons.Default.DeleteOutline,
                contentDescription = "Delete transaction",
                tint = AppTextMuted,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/* ---------------------------------- HELPERS -------------------------------- */

/** Formats an amount as "₹1,240" with thousands grouping. */
fun money(amount: Double): String = "₹%,.0f".format(amount)

fun formatTime(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
