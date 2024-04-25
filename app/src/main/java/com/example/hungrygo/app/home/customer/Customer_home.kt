package com.example.hungrygo.app.home.customer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.app.home.customer.addroom.Room
import com.example.hungrygo.databinding.CustomerHomeBinding
import com.google.android.material.appbar.MaterialToolbar

class Customer_home : Basic_Activity<CustomerHomeBinding, Customer_home_viewmodel>(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm=viewModel
        viewModel.navigator=this

    }

    override fun initViewmodel(): Customer_home_viewmodel {
        return ViewModelProvider(this).get(Customer_home_viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.customer_home
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return null
    }

    override fun navigate_to_addroom() {
        val intent = Intent(this, Room::class.java)
        startActivity(intent)
    }
}