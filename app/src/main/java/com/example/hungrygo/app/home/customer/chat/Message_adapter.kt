package com.example.hungrygo.app.home.customer.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Message
import com.example.hungrygo.databinding.ItemChatReceivedBinding
import com.example.hungrygo.databinding.ItemChatSendBinding

class Message_adapter:Adapter<RecyclerView.ViewHolder>() {
    val items= mutableListOf<Message>()

    class SendMessage(val dataBinding: ItemChatSendBinding) : RecyclerView.ViewHolder(dataBinding.root){
        fun bind(message: Message){
            dataBinding.item=message
            dataBinding.executePendingBindings()
        }
    }
    class ReceivedMessage(val dataBinding: ItemChatReceivedBinding) : RecyclerView.ViewHolder(dataBinding.root){
        fun bind(message: Message){
            dataBinding.item=message
            dataBinding.executePendingBindings()
        }
    }

    val SEND=1
    val RECEIVED=2
    override fun getItemViewType(position: Int): Int {
        val message=items.get(position)
        if (message.senderId==DataUtils.appUser_customer?.id){
            return SEND
        }else{
            return RECEIVED
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==SEND){
            val view:ItemChatSendBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_chat_send,parent,false)
            return SendMessage(view)
        }else{
            val view:ItemChatReceivedBinding =DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_chat_received,parent,false)
            return ReceivedMessage(view)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is SendMessage){
            holder.bind(items.get(position))
        }else if(holder is ReceivedMessage){
            holder.bind(items.get(position))
        }
    }

    fun setList(Newlist:MutableList<Message>) {
        items.addAll(Newlist)
        notifyItemRangeChanged(items.size+1,Newlist.size)
    }
}