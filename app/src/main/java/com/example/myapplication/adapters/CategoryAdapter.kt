package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.models.CategoryModel
import com.example.myapplication.R


class CategoryAdapter(
    private val categoryList: ArrayList<CategoryModel>,
    private val context: Context
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.expense_category,
            parent, false
        )
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryName.text = categoryList[position].categoryTitle
        holder.categoryImg.setImageResource(categoryList[position].categoryImg)
        holder.cardView.setOnClickListener{
            
        }
    }

    override fun getItemCount(): Int {

        return categoryList.size
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.idTVCourse)
        val categoryImg: ImageView = itemView.findViewById(R.id.idIVCourse)
        var cardView : CardView = itemView.findViewById(R.id.cardView)
    }
}
