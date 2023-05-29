package com.example.myapplication.acitivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.myapplication.R
import java.text.DecimalFormat

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
//        dropdownRow : Button = findViewById(R.id.dropdownRow)
        dropdown1 = findViewById(R.id.dropdown1)
        dropdown2 = findViewById(R.id.dropdown2)


        // Rest of your code...
    }

    private fun calculateDollarAmount()
    {
        val tvAmount : TextView = findViewById(R.id.amount)
        val tvDollarAmount : TextView = findViewById(R.id.dollar_amount)

        var taka = tvAmount.text.toString().toDouble()
        var dollar = taka/107.36

        tvDollarAmount.text = String.format("%.2f", dollar)
    }
    fun onDigitPressed(view: View) {
        val button7 : Button = findViewById(R.id.button7)
        val button8 : Button = findViewById(R.id.button8)
        val button9 : Button = findViewById(R.id.button9)
        val button4 : Button = findViewById(R.id.button4)
        val button5 : Button = findViewById(R.id.button5)
        val button6 : Button = findViewById(R.id.button6)

        val button1 : Button = findViewById(R.id.button1)
        val button2 : Button = findViewById(R.id.button2)
        val button3 : Button = findViewById(R.id.button3)

        val buttonDot : Button = findViewById(R.id.buttonDot)
        val button0 : Button = findViewById(R.id.button0)
        val buttonBack : Button = findViewById(R.id.buttonBack)


        val tvAmount : TextView = findViewById(R.id.amount)

        if(tvAmount.text == "0") tvAmount.text = (view as Button).text
        else tvAmount.append((view as Button).text)
        calculateDollarAmount()
    }

    fun onClearPressed(view: View){
        val clearBtn : ImageButton = findViewById(R.id.clear_btn)
        val tvAmount : TextView = findViewById(R.id.amount)

        tvAmount.text = "0"
        calculateDollarAmount()
    }

    // Rest of the AddExpenseActivity class...
}
