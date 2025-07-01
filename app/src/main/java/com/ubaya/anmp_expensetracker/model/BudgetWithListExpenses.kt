package com.ubaya.anmp_expensetracker.model

import androidx.room.Embedded
import androidx.room.Relation

data class BudgetWithListExpenses(
    @Embedded val budget: Budget,

    @Relation(
        parentColumn = "uuid",
        entityColumn = "budget_id"
    )
    val expenses: List<Expense>
)
