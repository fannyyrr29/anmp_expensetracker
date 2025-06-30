package com.ubaya.anmp_expensetracker.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExpenseWithBudget(
    @Embedded
    val expense: Expense,

    @Relation(
        parentColumn = "budget_id",
        entityColumn = "uuid"
    )
    val budget: Budget
)
