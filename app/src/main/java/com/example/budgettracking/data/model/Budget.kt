package com.example.budgettracking.data.model

data class Budget(
    val id: String,
    val name: String,
    val income: Long,
    val target: Long
)
