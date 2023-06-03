package com.example.myapplication.adapters

import Expense
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.util.*

class ExpRecyclerAdapter(var expArray: ArrayList<Expense>) : RecyclerView.Adapter<ExpRecyclerAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(expense: Expense)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expImg: ImageView = itemView.findViewById(R.id.expImg)
        val expName: TextView = itemView.findViewById(R.id.textViewExpenseName)
        val expAmt: TextView = itemView.findViewById(R.id.textViewExpAmount)
        val expTime: TextView = itemView.findViewById(R.id.textViewExpenseTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_design, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return expArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = expArray[position]
        // Set the values from the Expense object to the views in the ViewHolder
        holder.expName.text = currentItem.expenseType
        when(holder.expName.text){
            "Groceries" -> holder.expImg.setImageResource(R.drawable.ic_groceries)
            "Pets" -> holder.expImg.setImageResource(R.drawable.ic_pet)
            "Entertainment" -> holder.expImg.setImageResource(R.drawable.ic_entertainment)
            "Snacks" -> holder.expImg.setImageResource(R.drawable.burger)
            "Transport" -> holder.expImg.setImageResource(R.drawable.ic_transportation)
            "Study" -> holder.expImg.setImageResource(R.drawable.ic_study)
            "Clothing" -> holder.expImg.setImageResource(R.drawable.ic_clothing)
            "Subscription" -> holder.expImg.setImageResource(R.drawable.ic_subscription)
            "Accessories" -> holder.expImg.setImageResource(R.drawable.ic_tools)
            "Meals" -> holder.expImg.setImageResource(R.drawable.ic_restaurant)
        }
        holder.expTime.text = currentItem.date
        holder.expAmt.text = "-BDT " + currentItem.amount

        holder.itemView.setOnClickListener {
            listener?.onItemClick(currentItem)
        }
    }

    fun setData(expenses: ArrayList<Expense>) {
        this.expArray = expenses
        notifyDataSetChanged()
    }
}
