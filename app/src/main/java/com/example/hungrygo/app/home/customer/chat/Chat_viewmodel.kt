package com.example.hungrygo.app.home.customer.chat

import androidx.databinding.ObservableField
import com.example.chat.Basic.Basic_Viewmodel
import com.example.hungrygo.DataUtils
import com.example.hungrygo.app.home.customer.addroom.Room_data
import java.util.Date

class Chat_viewmodel:Basic_Viewmodel<Navigator>() {
    val messagefiled=ObservableField<String>()
    var room: Room_data?=null

    fun sendmessage(){
        val message= Message(
            content = messagefiled.get(),
            roomId = room?.id,
            senderId = DataUtils.appUser_customer?.id,
            sendername = DataUtils.appUser_customer?.name,
            datetime = Date().time
        )
    }
}