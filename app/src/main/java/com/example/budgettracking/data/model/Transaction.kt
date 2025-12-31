package com.example.budgettracking.data.model
data class Transaction(
    val id: String,
    val title: String,
    val amount: Double, // If this is Double, use 500.0
    val type: TransactionType
)