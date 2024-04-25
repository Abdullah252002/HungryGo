package com.example.hungrygo.app.home.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.hungrygo.R
import com.example.hungrygo.app.home.customer.addroom.Room_data
import com.example.hungrygo.databinding.ItemRoomBinding

class adpter_room(var items: List<Room_data>?) :Adapter<adpter_room.viewholder>(){

    class viewholder(val dataBinding: ItemRoomBinding) : RecyclerView.ViewHolder(dataBinding.root){
        fun bind(room:Room_data){
            dataBinding.item=room
            dataBinding.invalidateAll()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view:ItemRoomBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_room,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items!![position])
    }
}