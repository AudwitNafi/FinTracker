package com.example.myapplication.models

data class Expense (
    var userId : String? =null,
    var transactionType : String?=null,
    var expenseType : String? =null,
    var date : String?=null,
    var note : String,
    var amount : String ?=null,
    )