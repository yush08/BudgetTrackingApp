package com.example.budgettracking.data.model

data class TransactionItemUi(
    val id: Int,
    val title: String,
    val description: String,
    val amount: String,
    val time: String,
    val isIncome: Boolean
)
