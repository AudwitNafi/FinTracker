package com.example.myapplication.acitivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.myapplication.R

class AddExpenseActivity : AppCompatActivity() {
    private lateinit var dropdownRow: LinearLayout
    private lateinit var dropdown1: Spinner
    private lateinit var dropdown2: Spinner
    private lateinit var saveButton: Button
    private lateinit var buttonRow1: LinearLayout
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var buttonRow2: LinearLayout
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var buttonRow3: LinearLayout
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var buttonRow4: LinearLayout
    private lateinit var buttonDot: Button
    private lateinit var button0: Button
    private lateinit var buttonBack: Button
    private lateinit var date: TextView
    private lateinit var note: TextView
    private lateinit var title: TextView
    private lateinit var cancelButton: TextView
    private lateinit var dollarIcon: ImageView
    private lateinit var amount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        // Assign variables to layout items
        dropdownRow = findViewById(R.id.dropdownRow)
        dropdown1 = findViewById(R.id.dropdown1)
        dropdown2 = findViewById(R.id.dropdown2)
        saveButton = findViewById(R.id.saveButton)
        buttonRow1 = findViewById(R.id.buttonRow1)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        buttonRow2 = findViewById(R.id.buttonRow2)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        buttonRow3 = findViewById(R.id.buttonRow3)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        buttonRow4 = findViewById(R.id.buttonRow4)
        buttonDot = findViewById(R.id.buttonDot)
        button0 = findViewById(R.id.button0)
        buttonBack = findViewById(R.id.buttonBack)
        date = findViewById(R.id.date)
        note = findViewById(R.id.note)
        title = findViewById(R.id.title)
        cancelButton = findViewById(R.id.cancel_button)
        dollarIcon = findViewById(R.id.dollarIcon)
        amount = findViewById(R.id.amount)

        // Rest of your code...
    }

    // Rest of the AddExpenseActivity class...
}
