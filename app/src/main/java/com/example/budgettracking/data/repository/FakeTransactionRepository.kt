package com.example.budgettracking.data.repository

import com.example.budgettracking.data.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeTransactionRepository : TransactionRepository {

    private val _transactions =
        MutableStateFlow<List<Transaction>>(emptyList())

    override val transactions: StateFlow<List<Transaction>>
        get() = _transactions

    override fun addTransaction(transaction: Transaction) {
        _transactions.value = _transactions.value + transaction
    }
}
