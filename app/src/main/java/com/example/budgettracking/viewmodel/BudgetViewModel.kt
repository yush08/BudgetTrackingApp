package com.example.budgettracking.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.budgettracking.data.model.Budget
import com.example.budgettracking.data.repository.BudgetRepository

class BudgetViewModel : ViewModel() {

    var hasCompletedSetup by mutableStateOf(false)
        private set

    private val repo = BudgetRepository()

    var tempIncome: Long = 0L
        private set

    fun saveIncome(income: Long) {
        tempIncome = income
    }

    fun hasBudget(): Boolean = repo.hasBudget()

    fun createBudget(
        name: String,
        income: Long,
        target: Long
    ) {
        repo.saveBudget(
            Budget(
                id = System.currentTimeMillis().toString(),
                name = name,
                income = income,
                target = target
            )
        )

        hasCompletedSetup = true
    }
}
