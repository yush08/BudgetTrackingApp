package com.example.budgettracking.data.repository

import com.example.budgettracking.data.model.Transaction
import kotlinx.coroutines.flow.StateFlow

interface TransactionRepository {

    val transactions: StateFlow<List<Transaction>>

    fun addTransaction(transaction: Transaction)
}
