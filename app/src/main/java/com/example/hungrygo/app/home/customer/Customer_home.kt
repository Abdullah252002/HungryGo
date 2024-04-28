package com.example.hungrygo.app.home.customer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hungrygo.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Customer_home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.customer_home)

        val id=findViewById<TextView>(R.id.id)
        id.setOnClickListener {
            Firebase.auth.signOut()
        }

    }
}