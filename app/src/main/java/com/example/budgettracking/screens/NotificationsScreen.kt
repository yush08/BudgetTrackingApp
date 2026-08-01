package com.example.budgettracking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.budgettracking.viewmodel.TransactionViewModel

@Composable
fun NotificationsScreen(
    viewModel: TransactionViewModel = viewModel(),
    onBack: () -> Unit = {}
) {
    val transactions by viewModel.transactions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 16.dp)
    ) {
        BackTopBar(title = "Notifications", onBack = onBack)

        if (transactions.isEmpty()) {
            EmptyNotifications()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(transactions) { tx ->
                    NotificationCard(tx)
                }
            }
        }
    }
}

@Composable
private fun NotificationCard(tx: Transaction) {
    val isExpense = tx.type == TransactionType.EXPENSE
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppSurface)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(AppSurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (isExpense) Icons.Default.NorthEast else Icons.Default.SouthWest,
                null,
                tint = if (isExpense) AppExpense else AppAccent,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (isExpense) "Expense recorded" else "Income received",
                color = AppTextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Text(
                text = (if (isExpense) "You spent " else "You received ") +
                        money(tx.amount) + " on " + tx.title,
                color = AppTextSecondary,
                fontSize = 12.sp,
                maxLines = 2
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = formatTime(tx.timestamp),
                color = AppTextMuted,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun EmptyNotifications() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.NotificationsOff, null, tint = AppTextSecondary, modifier = Modifier.size(48.dp))
        Spacer(Modifier.height(12.dp))
        Text("You're all caught up", color = AppTextPrimary, fontWeight = FontWeight.SemiBold)
        Text("No notifications right now", color = AppTextSecondary, fontSize = 13.sp)
    }
}
