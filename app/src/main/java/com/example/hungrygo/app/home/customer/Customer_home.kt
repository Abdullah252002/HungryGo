package com.example.hungrygo.app.home.customer

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.databinding.CustomerHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Customer_home : AppCompatActivity() {
    lateinit var dataBinding:CustomerHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.customer_home)
        open_signout()


    }
    fun open_signout(){
        dataBinding.appBarRestaurantHome.menu.setOnClickListener {
            dataBinding.drawerLayout.open()
        }
        dataBinding.signout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}