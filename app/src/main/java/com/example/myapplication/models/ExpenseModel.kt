package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("exp")
data class ExpenseModel (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val expImg : Int,
    val expTitle : String,
    val expNote : String,
    val expTime : String,
    val expAmt : Double){

}