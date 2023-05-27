package com.example.myapplication

import androidx.room.*
import com.example.myapplication.models.ExpenseModel

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM exp")
    fun getAll(): List<ExpenseModel>

    @Insert
    fun insertAll(vararg expense: ExpenseModel)

    @Delete
    fun delete(vararg expense: ExpenseModel)

    @Update
    fun update(vararg expense: ExpenseModel)
}