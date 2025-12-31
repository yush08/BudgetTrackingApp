package com.example.budgettracking.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgettracking.data.model.Transaction
import com.example.budgettracking.data.model.TransactionType
import com.example.budgettracking.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onAddTransactionClick: () -> Unit,
    homeViewModel: HomeViewModel = viewModel()
) {
    val transactions by homeViewModel.transactions.collectAsState()

    val totalBalance = transactions.sumOf {
        if (it.type == TransactionType.INCOME) it.amount else -it.amount
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTransactionClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaction"
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Total Balance",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Rs. $totalBalance",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Transaction History",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}


@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = transaction.title)
                Text(
                    text = transaction.type.name,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = if (transaction.type == TransactionType.INCOME)
                    "+ Rs.${transaction.amount}"
                else
                    "- Rs.${transaction.amount}",
                color = if (transaction.type == TransactionType.INCOME)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error
            )
        }
    }
}
