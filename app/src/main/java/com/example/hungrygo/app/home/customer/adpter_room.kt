package com.example.hungrygo.app.home.customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Room_data
import com.example.hungrygo.databinding.ItemRoomBinding
import com.google.android.material.textfield.TextInputLayout

class adpter_room(var items: List<Room_data?>?) : Adapter<adpter_room.viewholder>() {

    class viewholder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val title1: TextView = itemview.findViewById(R.id.title1)
        val title2: TextView = itemview.findViewById(R.id.title2)
        val textbox: TextInputLayout = itemview.findViewById(R.id.password_layout)
        val button: Button = itemview.findViewById(R.id.login)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view1: ItemRoomBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_room,
            parent,
            false
        )
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        //  holder.bind(items!![position])
        val item = items?.get(position)
        holder.title1.setText(item?.name)
        holder.title2.setText(item?.details)
        holder.button.setOnClickListener {
            onItemClickLister.onitem(item!!, position, holder)
        }

    }

    fun changedata(rooms: List<Room_data>) {
        items = rooms
        notifyDataSetChanged()
    }

    lateinit var onItemClickLister: OnItemClickLister

    interface OnItemClickLister {
        fun onitem(item: Room_data, position: Int, holder: viewholder)
    }

}