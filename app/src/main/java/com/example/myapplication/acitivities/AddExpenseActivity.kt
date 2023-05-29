package com.example.myapplication.acitivities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    private lateinit var note: EditText
    private lateinit var title: TextView
    private lateinit var cancelButton: TextView
    private lateinit var dollarIcon: ImageView
    private lateinit var amount: TextView


    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)


        // Assign variables to layout items
//        dropdownRow : Button = findViewById(R.id.dropdownRow)
        dropdown1 = findViewById(R.id.dropdown1)
        dropdown2 = findViewById(R.id.dropdown2)


        // Rest of your code...
    }


    private fun saveExpenseData(){

    }

    private fun getTransactionType(): String {
        dropdown1 = findViewById(R.id.dropdown1)
        return dropdown1.selectedItem.toString()
    }

    private fun getExpenseType() : String{
        dropdown2 = findViewById(R.id.dropdown2)
        return dropdown2.selectedItem.toString()
    }

    private fun getNote() : String {

        note = findViewById(R.id.note)
        return note.text.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTime(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return LocalDateTime.now().format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCurrentTime(){
        date = findViewById(R.id.date)
        date.text = getCurrentTime()
    }

    private fun calculateDollarAmount(){
        amount = findViewById(R.id.amount)
        val tvDollarAmount : TextView = findViewById(R.id.dollar_amount)

        var taka = amount.text.toString().toDouble()
        var dollar = taka/107.36

        tvDollarAmount.text = String.format("%f", dollar)
    }
    fun onDigitPressed(view: View) {

        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        button4= findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6  = findViewById(R.id.button6)

        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3  = findViewById(R.id.button3)

        buttonDot  = findViewById(R.id.buttonDot)
        button0  = findViewById(R.id.button0)
        buttonBack  = findViewById(R.id.buttonBack)


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
