package com.example.hungrygo.app.home.restaurant.app_bar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.hungrygo.R


class Adapter_customer_evaluation(var items: HashMap<String, Double>?) :
    Adapter<Adapter_customer_evaluation.Viewholder>() {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number: TextView = itemView.findViewById(R.id.number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_customer_evaluation, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val key = items?.keys?.toList()
        val item =key?.get(position)
        holder.number.text = item
    }

    fun setlist(hashMap: HashMap<String, Double>?){
        items=hashMap
        notifyDataSetChanged()
    }


}