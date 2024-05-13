package com.example.hungrygo.app.home.restaurant

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.databinding.ProfileRestaurantBinding

class Profile_Restaurant : AppCompatActivity() {
    lateinit var dataBinding: ProfileRestaurantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding= DataBindingUtil.setContentView(this,R.layout.profile_restaurant)
        val data=DataUtils.appuser_Restaurant
        dataBinding.restaurantName.text=data?.restaurant_name
        dataBinding.phoneNumber.text=data?.restaurant_mobile_number
        dataBinding.email.text=data?.email


    }
}