package com.ubaya.anmp_expensetracker.util

import android.content.Context
import com.ubaya.anmp_expensetracker.model.ExpensesDatabase
import java.text.NumberFormat
import java.util.Locale

val DB_NAME = "expensetracker"

fun buildDb(context:Context):ExpensesDatabase{
    val db = ExpensesDatabase.buildDatabase(context)
    return db
}

fun formatRupiah(nominal: Double): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    return format.format(nominal)
}


