package com.example.hungrygo.app.home.restaurant.fragment.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.databinding.ItemOrdersBinding

class Adapter_test : RecyclerView.Adapter<Adapter_test.Viewholder>() {
    var items: List<Item_Orders?>?=null
    class Viewholder(val dataBinding: ItemOrdersBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(itemOrders: Item_Orders) {
            dataBinding.items = itemOrders
            dataBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val dataBinding: ItemOrdersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_orders,
            parent,
            false
        )
        return Viewholder(dataBinding)
    }

    override fun getItemCount(): Int {
        return items?.count()?:0
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(items?.get(position)!!)
    }

    fun setlist(newitems: List<Item_Orders>) {
        items= newitems.toMutableList()
        notifyDataSetChanged()
    }



}