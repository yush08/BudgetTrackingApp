package com.example.budgettracking.data.model

import com.example.budgettracking.data.model.TransactionType
data class Transaction(
    val id: Long,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)