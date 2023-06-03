package com.example.myapplication.acitivities

import Expense
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val expense: Expense? = intent.getSerializableExtra("expense") as? Expense

        val tvAmount: TextView = findViewById(R.id.amount)
        val tvCategory: TextView = findViewById(R.id.category)
        val tvDate: TextView = findViewById(R.id.date)
        val tvPaymentMethod: TextView = findViewById(R.id.payment_method)
        val tvNote: TextView = findViewById(R.id.note)
        val backBtn: ImageButton = findViewById(R.id.back_btn)

        backBtn.setOnClickListener {
            finish()
        }

        expense?.let {
            tvAmount.text = it.amount
            tvCategory.text = it.expenseType
            tvDate.text = it.date
            tvPaymentMethod.text = it.paymentMethod
            tvNote.text = it.note
        }
    }
}
