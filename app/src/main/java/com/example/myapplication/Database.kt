package com.example.myapplication

import androidx.room.RoomDatabase
import com.example.myapplication.models.ExpenseModel


@androidx.room.Database(entities = [ExpenseModel::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun expenseDao() : ExpenseDao
}