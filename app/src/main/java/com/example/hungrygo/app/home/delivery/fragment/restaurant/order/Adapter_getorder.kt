package com.example.hungrygo.app.home.delivery.fragment.restaurant.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.databinding.ItemPendingResBinding

class Adapter_getorder(var items: List<Item_request>?): RecyclerView.Adapter<Adapter_getorder.Viewholer>() {
    class Viewholer(val dataBinding: ItemPendingResBinding) : RecyclerView.ViewHolder(dataBinding.root){
        fun bind(itemRequest: Item_request){
            dataBinding.item=itemRequest
            dataBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholer {
        val dataBinding: ItemPendingResBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_pending_res,
            parent,
            false)
        return Viewholer(dataBinding)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    override fun onBindViewHolder(holder: Viewholer, position: Int) {
        val item=items?.get(position)
        holder.dataBinding.refused.visibility=View.GONE
        holder.dataBinding.profile.visibility=View.GONE
        holder.bind(item!!)
        holder.dataBinding.accept.setOnClickListener {
            clickListener?.onItemClicked(item,holder)
        }
    }

    fun setlist(list: List<Item_request>) {
        items=list
        notifyDataSetChanged()
    }
    var clickListener: ClickListener? = null
    interface ClickListener{
        fun onItemClicked(itemRequest: Item_request,holder: Viewholer)
    }
}