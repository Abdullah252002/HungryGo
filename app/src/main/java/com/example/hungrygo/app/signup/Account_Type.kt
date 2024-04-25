package com.example.hungrygo.app.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.signup.customer.Customer_signup
import com.example.hungrygo.app.signup.delivery.Delivery_signup
import com.example.hungrygo.app.signup.restaurant.Restaurant_signup
import com.example.hungrygo.databinding.ActivityAccountTypeBinding


class Account_Type : AppCompatActivity() {
    lateinit var dataBinding: ActivityAccountTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_type)

        dataBinding.customer.setOnClickListener {
            val intent = Intent(this, Customer_signup::class.java)
            startActivity(intent)
            finish()
        }
        dataBinding.delivery.setOnClickListener {
            val intent = Intent(this, Delivery_signup::class.java)
            startActivity(intent)
            finish()

        }
        dataBinding.restaurant.setOnClickListener {
            val intent = Intent(this, Restaurant_signup::class.java)
            startActivity(intent)
            finish()

        }

        setSupportActionBar(dataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""



    }

    //"${user.id ?: "unknown"}/image.jpg" FJvTgbrjKyNzpslxYq0duqtZ8sr1
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}