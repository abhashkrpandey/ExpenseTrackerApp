package com.mydomain.expensetracker.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.mydomain.expensetracker.enums.TransactionType

@Entity
data class Expense(
    val amount: Double,
    val dateOfExpense: Long,
    val description: String,
    val transactionType: TransactionType,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
)
