package com.example.hungrygo.app.home.delivery.fragment.restaurant.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.databinding.ItemOrdersDelBinding

class Adapter_get_orders(var items: List<Item_Orders>?) :
    RecyclerView.Adapter<Adapter_get_orders.ViewHoler>() {
    class ViewHoler(val dataBinding: ItemOrdersDelBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(itemOrders: Item_Orders) {
            dataBinding.items = itemOrders
            dataBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val dataBinding: ItemOrdersDelBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_orders_del, parent, false
        )
        return ViewHoler(dataBinding)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = items?.get(position)
        holder.bind(item!!)
        holder.dataBinding.add.setOnClickListener {
            clickListener?.onItemClick(item)
        }
    }
    var clickListener: ClickOnItemListener? = null
    interface ClickOnItemListener{
        fun onItemClick(itemorders: Item_Orders)
    }

    fun setlist(newitems: List<Item_Orders>) {
        items = newitems
        notifyDataSetChanged()
    }
}