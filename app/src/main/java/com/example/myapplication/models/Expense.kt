package com.example.myapplication.models

import android.os.Parcel
import android.os.Parcelable

data class Expense (
    var userId : String? =null,
    var transactionType : String?=null,
    var expenseType : String? =null,
    var paymentMethod: String? =null,
    var date : String?=null,
    var note : String?=null,
    var amount : String ?=null,
    ): Parcelable{

    constructor() : this("") {
        // Empty constructor body
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) =with(parcel){
        parcel.writeString(userId)
        parcel.writeString(transactionType)
        parcel.writeString(expenseType)
        parcel.writeString(paymentMethod)
        parcel.writeString(date)
        parcel.writeString(note)
        parcel.writeString(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }

}