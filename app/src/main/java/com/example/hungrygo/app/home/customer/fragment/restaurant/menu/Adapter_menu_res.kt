package com.example.hungrygo.app.home.customer.fragment.restaurant.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Image_Resturant

class Adapter_menu_res:Adapter<Adapter_menu_res.Viewholder>() {
    var items: List<Image_Resturant>? = null
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name_menu: TextView = itemView.findViewById(R.id.name_menu)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items?.get(position)
        holder.name_menu.setText(item?.image_name)
        Glide.with(holder.itemView)
            .load(item?.id)
            .into(holder.image)
        holder.itemView.setOnClickListener {
            clickOnItemListener?.onitem(position,item!!)
        }

    }
    fun setitems(newitems: MutableList<Image_Resturant>) {
        items=newitems
        notifyDataSetChanged()
    }

    var clickOnItemListener:ClickOnItemListener?=null
    interface ClickOnItemListener{
        fun onitem(position: Int,item: Image_Resturant)
    }

}