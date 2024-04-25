package com.example.hungrygo.app.home.customer.addroom

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.databinding.RoomBinding
import com.google.android.material.appbar.MaterialToolbar

class Room : Basic_Activity<RoomBinding, Room_viewmodel>(),Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm=viewModel
        viewModel.navigator=this
    }

    override fun initViewmodel(): Room_viewmodel {
        return ViewModelProvider(this).get(Room_viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.room
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return null
    }

    override fun backtohome() {
        finish()
    }
}