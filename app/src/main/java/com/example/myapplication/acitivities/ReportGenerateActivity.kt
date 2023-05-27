package com.example.myapplication.acitivities

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ReportGenerateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_generate)

        val pieChart = findViewById<PieChart>(R.id.pieChart)

        val entries = getChartData()

        val dataSet = PieDataSet(entries, "Categories")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.setEntryLabelTextSize(8f)
        pieChart.setEntryLabelColor(Color.BLACK)

        val interTypeface = ResourcesCompat.getFont(this, R.font.inter)
        pieChart.setEntryLabelTypeface(interTypeface)

        pieChart.animateY(1000)
        pieChart.invalidate()

    }

    private fun getChartData(): List<PieEntry> {
        // Replace this with your dynamic data retrieval logic
        return listOf(
            PieEntry(40f, "Grocery"),
            PieEntry(30f, "Drugs"),
            PieEntry(20f, "Entertainment"),
            PieEntry(10f, "Education")
        )
    }

    private fun getChartDataWeek(): List<PieEntry> {
        // Replace this with your dynamic data retrieval logic
        return listOf(
            PieEntry(40f, "Grocery"),
            PieEntry(30f, "Drugs"),
            PieEntry(20f, "Entertainment"),
            PieEntry(10f, "Education")
        )
    }

    private fun getChartDataMonth(): List<PieEntry> {
        // Replace this with your dynamic data retrieval logic
        return listOf(
            PieEntry(40f, "Grocery"),
            PieEntry(30f, "Drugs"),
            PieEntry(20f, "Entertainment"),
            PieEntry(10f, "Education")
        )
    }

    private fun getChartDataYear(): List<PieEntry> {
        // Replace this with your dynamic data retrieval logic
        return listOf(
            PieEntry(40f, "Grocery"),
            PieEntry(30f, "Drugs"),
            PieEntry(20f, "Entertainment"),
            PieEntry(10f, "Education")
        )
    }

}