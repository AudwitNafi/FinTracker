package com.example.myapplication.acitivities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.ExpRecyclerAdapter
import com.example.myapplication.models.Expense
import com.example.myapplication.R
import com.example.myapplication.acitivities.ReportGenerateActivity
import com.example.myapplication.firebase.FirestoreClass
import com.example.myapplication.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class HomePage : AppCompatActivity() {
    private lateinit var mUserName: String
    private lateinit var deleted: Expense
    private lateinit var old: ArrayList<Expense>
    private lateinit var expArray: ArrayList<Expense>
    private lateinit var expenseAdapter: ExpRecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        FirestoreClass().signInUser(this)

        // Setting NavBar
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.nav_expenses

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_reports -> {
                    startActivity(Intent(applicationContext, ReportGenerateActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_expenses -> return@OnNavigationItemSelectedListener true
                R.id.nav_profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        expArray = arrayListOf()
        expenseAdapter = ExpRecyclerAdapter(expArray)
        linearLayoutManager = LinearLayoutManager(this)

        val expRecyclerView: RecyclerView = findViewById(R.id.expRecyclerView)
        expRecyclerView.apply {
            adapter = expenseAdapter
            layoutManager = linearLayoutManager
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteExpense(expArray[viewHolder.adapterPosition])
            }
        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(expRecyclerView)

        // Go to add expense activity
        val addExpense: Button = findViewById(R.id.add_btn)
        addExpense.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    fun setUserData(user: User) {
        mUserName = user.name
        val tvName: TextView = findViewById(R.id.tv_username)
        tvName.text = "Welcome back, ${user.name}"
    }

    private fun updateTotalExpense() {
        var totalExp = 0.0
        for (expense in expArray) {
            totalExp += expense.amount?.toDouble() ?: 0.0
        }
        val expToday = findViewById<TextView>(R.id.totalExpToday)
        expToday.text = "$ %.2f".format(totalExp)
    }

    private fun undoDelete() {
        expArray = old

        runOnUiThread {
            expenseAdapter.setData(expArray)
            updateTotalExpense()
        }
    }

    private fun showSnackBar() {
        val view = findViewById<View>(R.id.coordinator)
        val snackBar = Snackbar.make(view, "Expense deleted!", Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo") {
            undoDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this, R.color.red))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .show()
    }

    private fun deleteExpense(expense: Expense) {
        deleted = expense
        old = expArray

        // TODO: Implement deletion from Firestore here
        // You can use FirestoreClass or any other Firestore library to delete the expense from the Firestore database
        // After successful deletion, update the expArray and UI accordingly

//        expArray = expArray.filter { it.id != expense.id } as ArrayList<Expense>
//        runOnUiThread {
//            updateTotalExpense()
//            expenseAdapter.setData(expArray)
//            showSnackBar()
//        }
    }

    override fun onResume() {
        super.onResume()
        // TODO: Fetch expenses from Firestore and update expArray
    }

}

