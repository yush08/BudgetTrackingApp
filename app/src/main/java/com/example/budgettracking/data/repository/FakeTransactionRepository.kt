package com.example.budgettracking.data.repository

import com.example.budgettracking.data.model.Transaction
import com.example.budgettracking.data.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * In-memory transaction store.
 *
 * This is a singleton [object] so every ViewModel (Home / Stats / Budgets /
 * AddExpense) reads and writes the SAME list — adding a transaction on one
 * screen is instantly reflected everywhere. It is pre-seeded with realistic
 * sample data so no screen ever appears empty in this frontend build.
 */
object FakeTransactionRepository : TransactionRepository {

    private const val DAY = 24 * 60 * 60 * 1000L

    private val _transactions =
        MutableStateFlow(seedTransactions())

    override val transactions: StateFlow<List<Transaction>>
        get() = _transactions

    override fun addTransaction(transaction: Transaction) {
        // Newest first
        _transactions.value = listOf(transaction) + _transactions.value
    }

    override fun removeTransaction(transaction: Transaction) {
        _transactions.value = _transactions.value.filterNot { it.id == transaction.id }
    }

    private fun seedTransactions(): List<Transaction> {
        val now = System.currentTimeMillis()
        fun daysAgo(d: Int) = now - d * DAY

        var id = now
        fun nextId() = id++

        return listOf(
            Transaction(nextId(), "Monthly Salary", 52000.0, TransactionType.INCOME, "October salary", daysAgo(6)),
            Transaction(nextId(), "House Rent", 15000.0, TransactionType.EXPENSE, "Apartment", daysAgo(6)),
            Transaction(nextId(), "Groceries", 2450.0, TransactionType.EXPENSE, "Weekly stock-up", daysAgo(5)),
            Transaction(nextId(), "Freelance Project", 8500.0, TransactionType.INCOME, "Logo design", daysAgo(4)),
            Transaction(nextId(), "Electricity Bill", 1820.0, TransactionType.EXPENSE, "Utility", daysAgo(4)),
            Transaction(nextId(), "Dinner Out", 1240.0, TransactionType.EXPENSE, "Restaurant", daysAgo(3)),
            Transaction(nextId(), "Cab Rides", 640.0, TransactionType.EXPENSE, "Commute", daysAgo(2)),
            Transaction(nextId(), "Online Shopping", 3299.0, TransactionType.EXPENSE, "Clothing", daysAgo(1)),
            Transaction(nextId(), "Subscriptions", 499.0, TransactionType.EXPENSE, "Streaming", daysAgo(1)),
            Transaction(nextId(), "Cashback Reward", 320.0, TransactionType.INCOME, "Card cashback", daysAgo(0)),
        )
    }
}
