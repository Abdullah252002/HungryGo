package com.example.hungrygo.app.home.restaurant.fragment.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.databinding.ItemOrdersBinding

class Adapter_item_orders : RecyclerView.Adapter<Adapter_item_orders.Viewholder>() {
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
        val item = items?.get(position)
        holder.bind(item!!)
        holder.dataBinding.delete.setOnClickListener {
            clickListener?.onClick(item)
        }
    }

    fun setlist(newitems: List<Item_Orders>) {
        items= newitems
        notifyDataSetChanged()
    }

    var clickListener: ClickOnItem? = null
    interface ClickOnItem {
        fun onClick(item: Item_Orders)
    }


}