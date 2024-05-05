package com.example.hungrygo.app.home.customer.fragment.shoping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Menu
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.databinding.ItemPendingBinding

class Adapter_shpoing_total(var items: List<Item_request>?) : Adapter<Adapter_shpoing_total.Viewholder>() {
    class Viewholder(val dataBinding: ItemPendingBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(itemRequest: Item_request){
            dataBinding.item=itemRequest
            dataBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view: ItemPendingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_pending,
            parent,
            false
        )
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item=items?.get(position)
        holder.bind(item!!)
        holder.dataBinding.delete.setOnClickListener {
            clickOnitemListener?.onitem(item)
        }
    }

    var clickOnitemListener:ClickOnitemListener?=null
    interface ClickOnitemListener{
        fun onitem(item:Item_request)
    }

    fun setlist(itemRequest: List<Item_request>) {
        items = itemRequest
        notifyDataSetChanged()
    }
}