package com.example.hungrygo.app.home.customer.addroom

import androidx.databinding.ObservableField
import com.example.chat.Basic.Basic_Viewmodel
import com.example.hungrygo.add_room_tofirebase
import com.example.hungrygo.app.model.Room_data
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener

class Room_viewmodel : Basic_Viewmodel<Navigator>() {
    val name_room = ObservableField<String>()
    val details = ObservableField<String>()

    val name_room_error = ObservableField<String>()
    val details_error = ObservableField<String>()



    fun setroom() {
        if (isvalid()) {

            add_room()
        }
    }

    fun add_room() {
        val room = Room_data(
            name = name_room.get(),
            details = details.get()
        )
        showDialog.value=true
        add_room_tofirebase(room, OnSuccessListener {
            showDialog.value=false
            navigator?.backtohome()
        }, OnFailureListener {
            showDialog.value=false
            messageLiveData.value=it.localizedMessage
        })

    }

    fun isvalid(): Boolean {
        var valid = true
        if (name_room.get().isNullOrBlank()) {
            name_room_error.set("Enter your Email")
            valid = false
        } else {
            name_room_error.set(null)
        }
        if (details.get().isNullOrBlank()) {
            details_error.set("Enter your Password")
            valid = false
        } else {
            details_error.set(null)
        }
        return valid
    }

}