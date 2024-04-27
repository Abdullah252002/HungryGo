package com.example.hungrygo.app.home.customer.chat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.app.home.customer.addroom.Room_data
import com.example.hungrygo.databinding.ChatBinding
import com.google.android.material.appbar.MaterialToolbar

class Chat : Basic_Activity<ChatBinding,Chat_viewmodel>(),Navigator {
    lateinit var room:Room_data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm=viewModel
        viewModel.navigator=this
        room=intent.getParcelableExtra("room")!!


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