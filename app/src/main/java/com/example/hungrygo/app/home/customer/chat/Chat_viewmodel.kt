package com.example.hungrygo.app.home.customer.chat

import androidx.databinding.ObservableField
import com.example.chat.Basic.Basic_Viewmodel
import com.example.hungrygo.DataUtils
import com.example.hungrygo.app.model.Room_data
import com.example.hungrygo.app.model.Message
import com.example.hungrygo.sendmessage_tofirebase
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.Date

class Chat_viewmodel : Basic_Viewmodel<Navigator>() {
    val messagefiled = ObservableField<String>()
    var room: Room_data? = null

    fun sendmessage() {
        val message = Message(
            content = messagefiled.get(),
            roomId = room?.id,
            senderId = DataUtils.appUser_customer?.id,
            sendername = DataUtils.appUser_customer?.name,
            datetime = Date().time
        )
        sendmessage_tofirebase(message,
            OnSuccessListener {
                messagefiled.set("")
            })
    }
}