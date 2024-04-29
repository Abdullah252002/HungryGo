package com.example.hungrygo.app.home.restaurant.fragment.menu

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.hungrygo.R

class Adapter_menu() : Adapter<Adapter_menu.Viewholder>() {
    var items: List<String>? = null
    var images:List<Uri>?=null


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
        val item_image = images?.getOrNull(position)
        holder.name_menu.setText(item)

        item_image?.let {
            Glide.with(holder.itemView)
                .load(it)
                .into(holder.image)
        }


    }

    fun setlist(it: List<String>?) {
        items = it
        notifyDataSetChanged()
    }

    fun setimage(it: List<Uri>) {
        images = it
    }


}