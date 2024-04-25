package com.example.hungrygo.app.signup.customer

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.databinding.CustomerSignupBinding
import com.google.android.material.appbar.MaterialToolbar

class Customer_signup : Basic_Activity<CustomerSignupBinding, Customer_signup_Viewmodel>(),
    Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm=viewModel
        viewModel.navigator=this


    }

    override fun initViewmodel(): Customer_signup_Viewmodel {
        return ViewModelProvider(this).get(Customer_signup_Viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.customer_signup
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return dataBinding.toolbar
    }

    override fun gotologin() {
      onBackPressed()
    }


}