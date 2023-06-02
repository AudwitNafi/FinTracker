package com.example.myapplication.acitivities
import Expense
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReportGenerateActivity : AppCompatActivity() {
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_generate)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.selectedItemId = R.id.nav_reports

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_expenses -> {
                    startActivity(Intent(applicationContext, HomePage::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_reports -> return@OnNavigationItemSelectedListener true
                R.id.nav_profile -> {
                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        pieChart = findViewById(R.id.pieChart)

        fetchExpenseData()
    }

    private fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    private fun fetchExpenseData() {
        val db = FirebaseFirestore.getInstance()
        val userId = getCurrentUserID()

        db.collection("Expenses")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val expenses = querySnapshot.toObjects(Expense::class.java)
                val entries = generateChartData(expenses)
                setupPieChart(entries)
            }
            .addOnFailureListener { exception ->
                // Handle any errors
            }
    }

    private fun generateChartData(expenses: List<Expense>): List<PieEntry> {
        val chartData = mutableListOf<PieEntry>()
        val expenseTypeMap = HashMap<String, Float>()

        for (expense in expenses) {
            val expenseType = expense.expenseType ?: ""
            val amount = expense.amount?.toFloat() ?: 0f

            if (expenseTypeMap.containsKey(expenseType)) {
                expenseTypeMap[expenseType] = expenseTypeMap[expenseType]!! + amount
            } else {
                expenseTypeMap[expenseType] = amount
            }
        }

        for ((expenseType, amount) in expenseTypeMap) {
            chartData.add(PieEntry(amount, expenseType))
        }

        return chartData
    }


    private fun setupPieChart(entries: List<PieEntry>) {
        val dataSet = PieDataSet(entries, null)
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = true
        pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER // Align legends in the center
        pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM // Align legends at the bottom
        pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL // Display legends in a horizontal layout
        pieChart.legend.setDrawInside(false) // Ensure legends are not drawn inside the pie chart
        pieChart.setDrawEntryLabels(false)
        pieChart.setDrawCenterText(false)
        pieChart.setEntryLabelTextSize(8f)
        pieChart.setEntryLabelColor(Color.BLACK)

        val interTypeface = ResourcesCompat.getFont(this, R.font.inter)
        pieChart.setEntryLabelTypeface(interTypeface)

        pieChart.animateY(1000)
        pieChart.invalidate()
    }

}

