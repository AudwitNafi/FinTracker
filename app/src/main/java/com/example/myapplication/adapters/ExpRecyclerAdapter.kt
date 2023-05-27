package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.ExpenseModel

class ExpRecyclerAdapter (var expArray : ArrayList<ExpenseModel>) : RecyclerView.Adapter<ExpRecyclerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val expImg : ImageView = itemView.findViewById(R.id.expImg)
        val expName : TextView = itemView.findViewById(R.id.textViewExpenseName)
        val expAmt : TextView = itemView.findViewById(R.id.textViewExpAmount)
        val expTime : TextView = itemView.findViewById(R.id.textViewExpenseTime)
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
        holder.expImg.setImageResource(currentItem.expImg)
        holder.expName.text = currentItem.expTitle
        holder.expTime.text = currentItem.expTime
        holder.expAmt.text = "$" + currentItem.expAmt.toString()
    }

    fun setData(expenses : ArrayList<ExpenseModel>){
        this.expArray = expenses
        notifyDataSetChanged()
    }
}