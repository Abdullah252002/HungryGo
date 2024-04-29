package com.example.hungrygo.app.home.restaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.home.restaurant.addphoto.AddPhoto
import com.example.hungrygo.app.home.restaurant.fragment.delivery.Delivery_fragment
import com.example.hungrygo.app.home.restaurant.fragment.menu.Menu_fragment
import com.example.hungrygo.app.home.restaurant.fragment.orders.Orders_fragment
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.RestaurantHomeBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Restaurant_home : AppCompatActivity() {
    val user = DataUtils.appuser_Restaurant
    lateinit var dataBinding: RestaurantHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.restaurant_home)
        addphoto()
        dataBinding.appBarRestaurantHome.BottomNavigation.setOnItemSelectedListener {
            if (it.itemId == R.id.menu) {
                PushFragment(Menu_fragment())
            } else if (it.itemId == R.id.orders) {
                PushFragment(Orders_fragment())
            } else if (it.itemId == R.id.delivery) {
                PushFragment(Delivery_fragment())
            }
            return@setOnItemSelectedListener true
        }
        PushFragment(Menu_fragment())

    }


    fun addphoto() {
        dataBinding.appBarRestaurantHome.menu.setOnClickListener {
            dataBinding.drawerLayout.open()
        }
        dataBinding.signout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        Handler(Looper.getMainLooper()).postDelayed({ checkphoto(user!!) }, 3000)
    }

    fun checkphoto(user: appUser_restaurant) {
        if (user.photo == false) {
            val intent = Intent(this, AddPhoto::class.java)
            intent.putExtra("id", user.id)
            startActivity(intent)
        }
    }

    fun PushFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
            .addToBackStack("").commit()
    }


}
