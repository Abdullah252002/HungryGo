package com.example.hungrygo.app.home.delivery.fragment.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.home.delivery.fragment.restaurant.order.Adapter_getorder
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.databinding.ItemPendingResBinding

class Adapter_orders_del(var items: List<Item_request>?) :
    RecyclerView.Adapter<Adapter_orders_del.Viewholer>() {

    class Viewholer(val dataBinding: ItemPendingResBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(itemRequest: Item_request) {
            dataBinding.item = itemRequest
            dataBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholer {
        val dataBinding: ItemPendingResBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_pending_res,
            parent,
            false
        )
        return Viewholer(dataBinding)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: Viewholer, position: Int) {
        val item = items?.get(position)
        holder.dataBinding.accept.setImageResource(R.drawable.profile)
        holder.dataBinding.refused.setImageResource(R.drawable.delete)
        holder.dataBinding.profile.setImageResource(R.drawable.resloc)
        holder.bind(item!!)
        holder.dataBinding.accept.setOnClickListener {
            clickListener?.cus_location(item)
        }
        holder.dataBinding.profile.setOnClickListener {
            clickListener?.res_location(item)
        }
        holder.dataBinding.refused.setOnClickListener {
            deleteItem?.delete(item)
        }

    }

    fun setlist(list: List<Item_request>) {
        items = list
        notifyDataSetChanged()
    }

    var clickListener: ClickListener? = null

    interface ClickListener {
        fun cus_location(item: Item_request)
        fun res_location(item: Item_request)
    }

    var deleteItem:Delete_item?=null
    interface Delete_item{
        fun delete(item: Item_request)
    }
}