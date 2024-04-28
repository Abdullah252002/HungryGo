package com.example.hungrygo.app.home.customer.chat

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Message
import com.example.hungrygo.app.model.Room_data
import com.example.hungrygo.databinding.ChatBinding
import com.example.hungrygo.getmessage_fromfirebase
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject

class Chat : Basic_Activity<ChatBinding,Chat_viewmodel>(),Navigator {
    lateinit var room: Room_data
    var adpter=Message_adapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm=viewModel
        viewModel.navigator=this
        room=intent.getParcelableExtra("room")!!
        viewModel.room=room
         getmessage_fromfirebase(room.id!!).orderBy("createdTimestamp", Query.Direction.ASCENDING)
             .addSnapshotListener{ snapshots, e->
         val newmessage= mutableListOf<Message>()
             for (dc in snapshots!!.documentChanges) {
                 when (dc.type) {
                     DocumentChange.Type.ADDED -> {
                         val message=dc.document.toObject(Message::class.java)
                         newmessage.add(message)
                     }
                     DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified city: ${dc.document.data}")
                     DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed city: ${dc.document.data}")
                 }
             }
         adpter.setList(newmessage)

         }

        dataBinding.recycle.adapter=adpter

    }

    override fun initViewmodel(): Chat_viewmodel {
        return ViewModelProvider(this).get(Chat_viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.chat
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return null
    }
}