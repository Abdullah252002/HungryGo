package com.example.hungrygo.app.home.restaurant.fragment.orders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.databinding.ItemPendingResBinding

class Adapter_orders(var items: List<Item_request>?) : Adapter<Adapter_orders.Viewholder>() {
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
        checked_acception(item!!, holder)
        holder.bind(item)
        holder.dataBinding.accept.setOnClickListener {
            clickOnitemListener?.Click_accept(item, holder)
        }
        holder.dataBinding.refused.setOnClickListener {
            clickOnitemListener?.Click_refused(item,holder)
        }
        holder.dataBinding.profile.setOnClickListener {
            clickOnitemListener?.Click_profile(item,holder)
        }
    }

    var clickOnitemListener: ClickOnitemListener? = null

    interface ClickOnitemListener {
        fun Click_accept(item: Item_request, holder: Viewholder)
        fun Click_refused(item: Item_request, holder: Viewholder)
        fun Click_profile(item: Item_request, holder: Viewholder)
    }

    fun setlist(itemRequest: List<Item_request>) {
        items = itemRequest
        notifyDataSetChanged()
    }

    fun checked_acception(item: Item_request, holder: Viewholder) {
        if (item.status == "Accept") {
            holder.dataBinding.accept.visibility = View.GONE
        } else {
            holder.dataBinding.accept.visibility = View.VISIBLE
        }
    }


}