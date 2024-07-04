package com.example.hungrygo.app.home.manger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_delivery

class Adapter_manger(val context: Context, var items: List<appUser_delivery>?) :
    Adapter<Adapter_manger.viewholder>() {
    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profile_image: ImageView = itemView.findViewById(R.id.image_profile)
        val name: TextView = itemView.findViewById(R.id.name)
        val status: TextView = itemView.findViewById(R.id.status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_delivery_profile, parent, false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val item = items?.get(position)
        Glide.with(holder.itemView).load(item?.image).into(holder.profile_image)
        holder.name.text = item?.name
        if (item?.status == "online") {
            holder.name.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.circle_online, 0, 0, 0
            )
        }else{
            holder.name.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.circle_offline, 0, 0, 0
            )
        }
        if(item?.job.equals("freelance")||item?.job.equals("Freelance")){
            holder.status.text = context.getString(R.string.freelance)
        }else{
            holder.status.text = item?.job
        }
        holder.itemView.setOnClickListener {
            onClickListner?.onItemClick(position,item!!)
        }

    }

    var onClickListner:OnClickListner?=null
    interface OnClickListner{
        fun onItemClick(position: Int, item: appUser_delivery)
    }
    fun setlist(newlist: List<appUser_delivery>) {
        items=newlist
        notifyDataSetChanged()
    }
}