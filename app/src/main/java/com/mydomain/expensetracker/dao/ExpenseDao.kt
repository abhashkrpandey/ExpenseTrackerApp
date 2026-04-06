package com.mydomain.expensetracker.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mydomain.expensetracker.entities.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
   suspend fun insertExpense(expense: Expense)

    @Query("select * from Expense" )
    fun fetchExpense(): Flow<List<Expense>>

    @Delete
   suspend fun deleteExpense(expense: Expense)

}