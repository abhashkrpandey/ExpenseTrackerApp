package com.mydomain.expensetracker.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Expense(
    val amount: Double,
    val dateOfExpense: Long,
    val category: String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
)
