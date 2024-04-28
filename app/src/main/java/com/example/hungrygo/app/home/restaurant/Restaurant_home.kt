package com.example.hungrygo.app.home.restaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.home.restaurant.addphoto.AddPhoto
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.RestaurantHomeBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Restaurant_home : Basic_Activity<RestaurantHomeBinding, Restaurant_home_viewmodel>(),
    Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.appBarRestaurantHome.menu.setOnClickListener {
            dataBinding.drawerLayout.open()
        }
        dataBinding.signout.setOnClickListener {
            Firebase.auth.signOut()
            val intent=Intent(this,Login::class.java)
            startActivity(intent)
            finish()
        }
        val user=DataUtils.appuser_Restaurant
        Handler(Looper.getMainLooper()).postDelayed({checkphoto(user!!)},3000)
    }

    override fun initViewmodel(): Restaurant_home_viewmodel {
        return ViewModelProvider(this).get(Restaurant_home_viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.restaurant_home
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return null
    }

    fun checkphoto(user:appUser_restaurant){
        if(user.photo==false){
            val intent=Intent(this,AddPhoto::class.java)
            intent.putExtra("id",user.id)
            startActivity(intent)
        }
    }

}