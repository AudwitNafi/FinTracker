package com.example.myapplication.acitivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myapplication.Database
import com.example.myapplication.models.CategoryModel
import com.example.myapplication.R
import com.example.myapplication.adapters.CategoryAdapter
import com.example.myapplication.models.ExpenseModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddExpenseActivity : AppCompatActivity() {

    lateinit var categoryRV: RecyclerView
    lateinit var categoryRVAdapter: CategoryAdapter
    lateinit var categoryList: ArrayList<CategoryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        categoryRV = findViewById(R.id.categoryGrid)

        categoryList = ArrayList()

        val layoutManager = GridLayoutManager(this, 4)

        categoryRV.layoutManager = layoutManager

        categoryRVAdapter = CategoryAdapter(categoryList, this)

        categoryRV.adapter = categoryRVAdapter


        populateCategories()

        categoryRVAdapter.notifyDataSetChanged()

        val saveBtn = findViewById<Button>(R.id.saveBtn)

        saveBtn.setOnClickListener {
            val amt = findViewById<EditText>(R.id.amount)
            val title = findViewById<EditText>(R.id.expTitle)
            val note = findViewById<EditText>(R.id.note)
            amt.addTextChangedListener{
                if(it!!.isNotEmpty())
                    amt.error = null
            }
            if(amt.text.toString().toDoubleOrNull()==null) amt.error = "Please enter an amount"
            else {
                val expense = ExpenseModel(0, R.drawable.burger, title.text.toString() , note.text.toString(), amt.text.toString().toDouble())
                insert(expense)
            }
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }

    private fun populateCategories()
    {
        categoryList.add(CategoryModel("Snacks", R.drawable.burger))
        categoryList.add(CategoryModel("Transport", R.drawable.ic_transportation))
        categoryList.add(CategoryModel("Meals", R.drawable.ic_restaurant))
        categoryList.add(CategoryModel("Study", R.drawable.ic_study))
        categoryList.add(CategoryModel("Tools", R.drawable.ic_tools))
        categoryList.add(CategoryModel("Subscription", R.drawable.ic_subscription))
        categoryList.add(CategoryModel("Medicine", R.drawable.ic_medicine))
        categoryList.add(CategoryModel("Groceries", R.drawable.ic_groceries))
        categoryList.add(CategoryModel("Clothing", R.drawable.ic_clothing))
        categoryList.add(CategoryModel("Pet", R.drawable.ic_pet))
        categoryList.add(CategoryModel("Misc.", R.drawable.soda))
        categoryList.add(CategoryModel("Lend", R.drawable.ic_lend))
    }

    private fun insert(expense : ExpenseModel)
    {
        val db = Room.databaseBuilder(this,
            Database::class.java,
            "Expenses").build()

        GlobalScope.launch {
            db.expenseDao().insertAll(expense)
        }
    }
}