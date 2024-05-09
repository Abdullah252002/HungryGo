package com.example.hungrygo.app.signup.restaurant

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.app.map.set_Location
import com.example.hungrygo.databinding.RestaurantSignupBinding
import com.google.android.material.appbar.MaterialToolbar

class Restaurant_signup : Basic_Activity<RestaurantSignupBinding, Restaurant_signup_Viewmodel>(),
    Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm=viewModel
        viewModel.navigator=this

        viewModel.location_error.observe(this, Observer {
            dataBinding.errorLocation.setTextColor(Color.RED)
            dataBinding.errorLocation.setText(it)
        })
    }

    override fun initViewmodel(): Restaurant_signup_Viewmodel {
        return ViewModelProvider(this).get(Restaurant_signup_Viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.restaurant_signup
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return dataBinding.toolbar
    }

    override fun gotologin() {
        onBackPressed()
    }

    override fun setlocation() {
        val intent = Intent(this, set_Location::class.java)
        startActivityForResult(intent, 1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val latitude = data?.getDoubleExtra("latitude", 0.0)
        val longitude = data?.getDoubleExtra("longitude", 0.0)
        viewModel.latitude.value=latitude
        viewModel.longitude.value=longitude


    }

}