package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgettracking.data.model.Transaction
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val background = Color(0xFF0E0E0E)
    val transactions by viewModel.transactions.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { HeaderSection() }
        item { SavingsCard() }
        item { AccountSummaryRow() }
        item { TransactionHistorySection(transactions) }
    }
}

/* ------------------------------------------------------ */
/* HEADER */
/* ------------------------------------------------------ */

@Composable
fun HeaderSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Lorem Ipsum", color = Color.White)
            }

            Row {
                Icon(Icons.Default.DarkMode, null, tint = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Icon(Icons.Default.Notifications, null, tint = Color.White)
            }
        }

        Text("Balance", color = Color.Gray)
        Text(
            text = "Rs. 0.00",
            color = Color(0xFF9CFF57),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/* ------------------------------------------------------ */
/* SAVINGS CARD */
/* ------------------------------------------------------ */

@Composable
fun SavingsCard() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text("Well done!", color = Color.White, fontWeight = FontWeight.Bold)
                Text(
                    "Your spending reduced by\n0% from last month.",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("View Details", color = Color(0xFF9CFF57))
            }

            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = 0f,
                    color = Color(0xFF9CFF57),
                    strokeWidth = 6.dp,
                    modifier = Modifier.size(70.dp)
                )
                Text("Rs 0", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

/* ------------------------------------------------------ */
/* ACCOUNT SUMMARY */
/* ------------------------------------------------------ */

@Composable
fun AccountSummaryRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard(
            title = "Account",
            amount = "Rs 0.00",
            icon = Icons.Default.AccountBalance,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            title = "Cash",
            amount = "Rs 0.00",
            icon = Icons.Default.Payments,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            title = "Borrowed",
            amount = "Rs 0.00",
            icon = Icons.Default.Person,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SummaryCard(
    title: String,
    amount: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1C))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(icon, null, tint = Color.White)
            Text(amount, color = Color.White, fontWeight = FontWeight.Bold)
            Text(title, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

/* ------------------------------------------------------ */
/* TRANSACTION HISTORY */
/* ------------------------------------------------------ */

@Composable
fun TransactionHistorySection(
    transactions: List<Transaction>
) {
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Transaction History", color = Color.White, fontWeight = FontWeight.Bold)
            Text("See All", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (transactions.isEmpty()) {
            Text(
                text = "No transactions yet",
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            transactions.take(5).forEach { transaction ->
                TransactionRow(transaction)
            }
        }
    }
}

@Composable
fun TransactionRow(
    transaction: Transaction
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                Icons.Default.Receipt,
                null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(transaction.title, color = Color.White)
                Text(
                    transaction.note.ifBlank { "No description" },
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = (if (transaction.type == TransactionType.EXPENSE) "- " else "+ ") +
                        "Rs ${transaction.amount}",
                color = if (transaction.type == TransactionType.EXPENSE)
                    Color.Red else Color(0xFF9CFF57)
            )
            Text(
                formatTime(transaction.timestamp),
                color = Color.Gray,
                fontSize = 11.sp
            )
        }
    }
}

/* ------------------------------------------------------ */
/* HELPERS */
/* ------------------------------------------------------ */

fun formatTime(timestamp: Long): String {
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(Date(timestamp))
}
