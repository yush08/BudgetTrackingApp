package com.example.budgettracking.data.repository

import com.example.budgettracking.data.model.Budget

class BudgetRepository {

    private val budgets = mutableListOf<Budget>()

    fun hasBudget(): Boolean {
        return budgets.isNotEmpty()
    }

    fun saveBudget(budget: Budget) {
        budgets.add(budget)
    }

    fun getBudgets(): List<Budget> {
        return budgets
    }
}
