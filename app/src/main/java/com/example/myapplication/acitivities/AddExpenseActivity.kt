package com.example.myapplication.acitivities

//import com.google.firebase.database.DatabaseReference
import Expense
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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


//    private lateinit var dbRef : DatabaseReference


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)


        // Assign variables to layout items
//        dropdownRow : Button = findViewById(R.id.dropdownRow)
        dropdown1 = findViewById(R.id.dropdown1)
        dropdown2 = findViewById(R.id.dropdown2)

        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            addExpense()
            finish()
        }
        // Rest of your code...
    }

    private fun getPaymentMethod(): String {
        dropdown1 = findViewById(R.id.dropdown1)
        return dropdown1.selectedItem.toString()
    }


    private fun  getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    private fun getAmount(): String {
        return findViewById<TextView>(R.id.amount).text.toString()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun addExpense() {
        val userId = getCurrentUserID()
        val expenseType = getExpenseType()
        val paymentMethod = getPaymentMethod()
        val note = getNote()
        val amount = getAmount()
        val dateTime = getCurrentTime()

        if (validateForm(expenseType, paymentMethod, amount)) {
            val expense = Expense(userId = userId, expenseType = expenseType, paymentMethod = paymentMethod, note = note, amount = amount, date = dateTime)

            val db = FirebaseFirestore.getInstance()
            val expenseCollection = db.collection("Expenses")

            val expenseDocument = expenseCollection.document()
            expense.id = expenseDocument.id // Assign the unique ID to the expense

            expenseDocument.set(expense)
                .addOnSuccessListener {
                    Toast.makeText(this, "Expense added successfully", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to add expense: ${exception.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_LONG).show()
        }
    }


    private fun validateForm(expenseType: String, paymentMethod: String, amount: String): Boolean{
        if(expenseType.isEmpty() || paymentMethod.isEmpty() || amount.isEmpty()) return false
        return true
    }

//    private fun getTransactionType(): String {
//        dropdown1 = findViewById(R.id.dropdown1)
//        return dropdown1.selectedItem.toString()
//    }

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
        val buttonId = view.id
        val tvAmount: TextView = findViewById(R.id.amount)

        when (buttonId) {
            R.id.buttonBack -> {
                val currentAmount = tvAmount.text.toString()
                if (currentAmount.length > 1) {
                    val newAmount = currentAmount.substring(0, currentAmount.length - 1)
                    tvAmount.text = newAmount
                } else {
                    tvAmount.text = "0"
                }
            }
            else -> {
                if (tvAmount.text.toString() == "0") {
                    tvAmount.text = (view as Button).text
                } else {
                    tvAmount.append((view as Button).text)
                }
            }
        }

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