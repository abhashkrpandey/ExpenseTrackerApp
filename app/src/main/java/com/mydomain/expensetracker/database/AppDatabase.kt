package com.mydomain.expensetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mydomain.expensetracker.dao.ExpenseDao
import com.mydomain.expensetracker.entities.Expense

@Database(entities = [Expense::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "expense_tracker"
            ).build().also { INSTANCE = it }
        }
    }
}