package com.example.hungrygo.app.home.customer.fragment.restaurant.item_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Menu
import com.example.hungrygo.app.model.appUser_restaurant

class Adapter_item_res(var items: List<Item_Menu>?) : Adapter<Adapter_item_res.Viewholder>() {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodname: TextView = itemView.findViewById(R.id.food_name)
        val content: TextView = itemView.findViewById(R.id.content)
        val price: TextView = itemView.findViewById(R.id.price)
        val image: ImageView = itemView.findViewById(R.id.image)
        val button: Button = itemView.findViewById(R.id.add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_details_res, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items?.get(position)
        Glide.with(holder.itemView)
            .load(item?.image)
            .into(holder.image)
        holder.foodname.setText(item?.food_name)
        holder.content.setText(item?.content)
        holder.price.setText(item?.price)
        holder.button.setOnClickListener {
            clickOnItemListener?.onitem(item!!,position)
        }
    }
    fun setlist(newitems: List<Item_Menu>) {
        items = newitems
        notifyDataSetChanged()
    }
    var clickOnItemListener:ClickOnItemListener?=null
    interface ClickOnItemListener{
        fun onitem(item: Item_Menu, position: Int)
    }
}