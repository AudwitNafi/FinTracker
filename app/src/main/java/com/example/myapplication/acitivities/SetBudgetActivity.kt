package com.example.myapplication.acitivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.firebase.FirestoreClass

class SetBudgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_budget)

        val saveBtn: Button = findViewById(R.id.save_budget_btn)

        saveBtn.setOnClickListener{
            setBudget()
        }
    }

    private fun setBudget()
    {
        val etSetBudget: EditText = findViewById(R.id.et_set_initial_budget)
        FirestoreClass().updateBudget(this, etSetBudget.text.toString())
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
        finish()
    }
}