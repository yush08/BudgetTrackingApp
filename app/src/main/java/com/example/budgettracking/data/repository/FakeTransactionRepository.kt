package com.example.budgettracking.data.repository
import com.example.budgettracking.data.model.Transaction
import com.example.budgettracking.data.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeTransactionRepository : TransactionRepository {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    override val transactions: StateFlow<List<Transaction>> = _transactions

    override fun addTransaction(transaction: Transaction) {
        _transactions.value = _transactions.value + transaction
    }
}
