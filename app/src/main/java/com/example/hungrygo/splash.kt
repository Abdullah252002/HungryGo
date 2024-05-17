package com.example.hungrygo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.hungrygo.app.home.delivery.Delivery_home
import com.example.hungrygo.app.home.restaurant.Restaurant_home
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.service.MyForegroundService
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        Handler(Looper.getMainLooper()).postDelayed({ Navigate() }, 2000)

    }


    private fun Navigate() {
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        val database = Firebase.database.reference
        database.child("user").get().addOnSuccessListener {
            if(it.value.toString().equals("test")){
                val firebaseUser = Firebase.auth.currentUser
                if (firebaseUser == null) {
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                    stopService(serviceIntent)
                } else {
                    startService(serviceIntent)
                    login_delivery_tofirestore(firebaseUser.uid,
                        OnSuccessListener {
                            val user = it.toObject(appUser_delivery::class.java)
                            if (user != null) {
                                DataUtils.appuser_Delivery = user
                                start_delivery_home()
                            }
                        })
                    login_resturant_tofirestore(firebaseUser.uid,
                        OnSuccessListener {
                            val user = it.toObject(appUser_restaurant::class.java)
                            if (user != null) {
                                DataUtils.appuser_Restaurant = user
                                start_restaurant_home()
                            }
                        })
                }

            }else{
                val intent = Intent(this, need_Update::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener {
            Log.e("eeeeeeeeeeeeeeeeeeeeeeeeee", it.message.toString())
        }


    }


    fun start_delivery_home() {
        val intent = Intent(this, Delivery_home::class.java)
        startActivity(intent)
        finish()
    }

    fun start_restaurant_home() {
        val intent = Intent(this, Restaurant_home::class.java)
        startActivity(intent)
        finish()
    }
}