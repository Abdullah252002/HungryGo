package com.example.hungrygo.app.home.customer.fragment.shoping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Menu

class Adapter_shoping(var items: List<Item_Menu>?) : Adapter<Adapter_shoping.Viewholder>() {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodname: TextView = itemView.findViewById(R.id.food_name)
        val content: TextView = itemView.findViewById(R.id.content)
        val price: TextView = itemView.findViewById(R.id.price)
        val image: ImageView = itemView.findViewById(R.id.image)
        val add: ImageView = itemView.findViewById(R.id.add_cont)
        val counter: TextView = itemView.findViewById(R.id.counter)
        val minus: ImageView = itemView.findViewById(R.id.minus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoping, parent, false)
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
        holder.price.setText(""+item?.price)
        holder.counter.setText(""+item?.counter)
        holder.add.setOnClickListener {
            add?.add_counter(item!!)
        }
        holder.minus.setOnClickListener {
            minus?.minus_counter(item!!)
        }
    }

    var add:ClickOnAdd?=null
    interface ClickOnAdd{
        fun add_counter(item:Item_Menu)
    }

    var minus:ClickOnMinus?=null

    interface ClickOnMinus{
        fun minus_counter(item:Item_Menu)
    }

    fun setlist(newitems: List<Item_Menu>) {
        items = newitems
        notifyDataSetChanged()
    }
}