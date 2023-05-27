package com.example.myapplication.acitivities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myapplication.Database
import com.example.myapplication.adapters.ExpRecyclerAdapter
import com.example.myapplication.models.ExpenseModel
import com.example.myapplication.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePage : AppCompatActivity() {
    private lateinit var deleted : ExpenseModel
    private lateinit var old : ArrayList<ExpenseModel>
    private lateinit var expArray : ArrayList<ExpenseModel>
    private lateinit var expenseAdapter: ExpRecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
//    private val expRecyclerView : RecyclerView = findViewById(R.id.expRecyclerView)

    lateinit var db : Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

//        val expRecyclerView : RecyclerView = findViewById(R.id.expRecyclerView)
//        expRecyclerView.layoutManager = LinearLayoutManager(this)

//        addExpenses()

        db = Room.databaseBuilder(this,
        Database::class.java,
        "exp").build()

        expArray = arrayListOf()
//        expRecyclerView.adapter = ExpRecyclerAdapter(expArray)
        expenseAdapter = ExpRecyclerAdapter(expArray)
        linearLayoutManager = LinearLayoutManager(this)

        val expRecyclerView : RecyclerView = findViewById(R.id.expRecyclerView)
        expRecyclerView.apply {
            adapter = expenseAdapter
            layoutManager = linearLayoutManager
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
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

        val addExpense = findViewById<Button>(R.id.add_btn)
        addExpense.setOnClickListener{
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAll(){

        GlobalScope.launch {
            expArray = db.expenseDao().getAll() as ArrayList<ExpenseModel>
//            db.expenseDao().insertAll(ExpenseModel(0, R.drawable.hotdog, "Evening", "Note", "18:40", 50.00))
            runOnUiThread{
                updateTotalExpense()
                expenseAdapter.setData(expArray)
            }

        }
    }
    private fun addExpenses()
    {
//        expArray.add(ExpenseModel(R.drawable.pizza_slice, "Pizza", "4:20", 2.30))
//        expArray.add(ExpenseModel(R.drawable.burger, "Burger", "3:15", 2.99))
//        expArray.add(ExpenseModel(R.drawable.donut, "Donut", "2:20", 0.99))
//        expArray.add(ExpenseModel(R.drawable.hotdog, "Hot dog", "2:15", 1.99))
//        expArray.add(ExpenseModel(R.drawable.soda, "Soda", "11.50", 0.50))
//        expArray.add(ExpenseModel(R.drawable.donut, "Donut", "14:20", 0.99))
//        expArray.add(ExpenseModel(R.drawable.hotdog, "Hot dog", "2:10", 1.99))
//        expArray.add(ExpenseModel(R.drawable.pizza_slice, "Pizza", "13:31", 2.30))
    }
    private fun updateTotalExpense(){
        val totalExp : Double = expArray.sumOf { it.expAmt }
        val expToday = findViewById<TextView>(R.id.totalExpToday)
        expToday.text = "$ %.2f".format(totalExp)
    }

    private fun undoDelete(){
        GlobalScope.launch {
            db.expenseDao().insertAll(deleted)

            expArray = old

            runOnUiThread {
                expenseAdapter.setData(expArray)
                updateTotalExpense()
            }
        }
    }

    private fun showSnackBar(){
        val view = findViewById<View>(R.id.coordinator)
        val snackBar = Snackbar.make(view, "Expense deleted!",Snackbar.LENGTH_LONG)
        snackBar.setAction("Undo"){
            undoDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this, R.color.red))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .show()
    }
    private fun deleteExpense(expense : ExpenseModel){
        deleted = expense
        old = expArray
        GlobalScope.launch {
            db.expenseDao().delete(expense)

            expArray = expArray.filter{ it.id != expense.id} as ArrayList<ExpenseModel>
            runOnUiThread{
                updateTotalExpense()
                expenseAdapter.setData(expArray)
                showSnackBar()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}