package com.example.hungrygo.app.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.app.home.customer.Customer_home
import com.example.hungrygo.app.home.delivery.Delivery_home
import com.example.hungrygo.app.home.restaurant.Restaurant_home
import com.example.hungrygo.app.signup.Account_Type
import com.example.hungrygo.databinding.LogInBinding
import com.google.android.material.appbar.MaterialToolbar


class Login : Basic_Activity<LogInBinding, Login_Viewmodel>(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm = viewModel
        viewModel.navigator = this

    }

    override fun initViewmodel(): Login_Viewmodel {
        return ViewModelProvider(this).get(Login_Viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.log_in
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return null
    }

    override fun navigate_to_signup() {
        val intent = Intent(this, Account_Type::class.java)
        startActivity(intent)
    }

    override fun navigate_customer_home() {
        val intent = Intent(this, Customer_home::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigate_delivery_home() {
        val intent = Intent(this, Delivery_home::class.java)
        startActivity(intent)
        finish()

    }

    override fun navigate_restaurant_home() {
        val intent = Intent(this, Restaurant_home::class.java)
        startActivity(intent)
        finish()
    }


}