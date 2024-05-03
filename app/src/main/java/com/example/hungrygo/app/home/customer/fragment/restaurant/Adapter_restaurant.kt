package com.example.hungrygo.app.home.customer.fragment.restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_restaurant

class Adapter_restaurant(var items: List<appUser_restaurant>?) : Adapter<Adapter_restaurant.Viewholder>() {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val res_name: TextView = itemView.findViewById(R.id.restaurant_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items?.get(position)
        Glide.with(holder.itemView).load(item?.image)
            .into(holder.image)
        holder.res_name.setText(item?.restaurant_name)
        holder.itemView.setOnClickListener {
            clickOnItemListener?.onitem(item!!,position)
        }
    }
    var clickOnItemListener:ClickOnItemListener?=null
    interface ClickOnItemListener{
        fun onitem(item:appUser_restaurant,position: Int)
    }

    fun setlist(m: List<appUser_restaurant>?) {
        items=m
        notifyDataSetChanged()
    }




    private var NewList: List<appUser_restaurant>? = null
    fun filterUsers(query: String) {
        if (NewList == null) {
            NewList = items
        }

        val getitemList = mutableListOf<appUser_restaurant>()

        if (query.isEmpty()) {
            getitemList.addAll(NewList ?: emptyList())
        } else {
            NewList?.forEach { user ->
                if (user.restaurant_name?.contains(query, ignoreCase = true) == true) {
                    getitemList.add(user)
                }
            }
        }
        setlist(getitemList)
    }
}