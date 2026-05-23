package com.mydomain.expensetracker.convertors

import androidx.room.TypeConverter
import com.mydomain.expensetracker.enums.TransactionType

class Convertors {
    @TypeConverter
    fun fromTransactionType(value: TransactionType):String
    {
        return value.name
    } 

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}