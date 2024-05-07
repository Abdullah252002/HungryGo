package com.example.hungrygo.app.home.restaurant.fragment.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.hungrygo.R
import com.example.hungrygo.app.home.restaurant.fragment.orders.Adapter_orders
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.databinding.ItemPendingResBinding

class Adapter_get_delivery (var items: List<Item_request>?) :Adapter<Adapter_get_delivery.Viewholder>(){

    class Viewholder(val dataBinding: ItemPendingResBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(itemRequest: Item_request) {
            dataBinding.item = itemRequest
            dataBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view: ItemPendingResBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_pending_res,
            parent,
            false
        )
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items?.get(position)
        holder.dataBinding.accept.visibility= View.GONE
        holder.dataBinding.profile.setImageResource(R.drawable.delivery_res)
        holder.dataBinding.refused.setImageResource(R.drawable.delete)
        holder.bind(item!!)
        holder.dataBinding.profile.setOnClickListener {
            clickListener?.delivery(item)
        }
        holder.dataBinding.refused.setOnClickListener {
            clickListener?.delete(item,holder)
        }
    }

    fun setlist(itemRequest: List<Item_request>) {
        items = itemRequest
        notifyDataSetChanged()
    }

    var clickListener: ClickListener? = null
    interface ClickListener {
        fun delivery(itemRequest: Item_request)
        fun delete(itemRequest: Item_request,holder: Viewholder)
    }
}