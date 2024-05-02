package com.example.hungrygo.app.home.customer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.home.customer.fragment.offers.Offers_fragment
import com.example.hungrygo.app.home.customer.fragment.restaurant.Restaurant_fragment
import com.example.hungrygo.app.home.customer.fragment.shoping.Shoping_fragment
import com.example.hungrygo.app.home.restaurant.fragment.menu.Menu_fragment
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.map.set_Location
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.databinding.CustomerHomeBinding
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.example.hungrygo.app.model.appUser_customer.Companion.Collection_name_customer


class Customer_home : AppCompatActivity() {
    lateinit var dataBinding: CustomerHomeBinding
    var currentuser: String? = null
    val restaurantFragment=Restaurant_fragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentuser = Firebase.auth.currentUser?.uid
        dataBinding = DataBindingUtil.setContentView(this, R.layout.customer_home)
        open_signout()
        dataBinding.appBarRestaurantHome.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.restaurant -> {
                    PushFragment(restaurantFragment)
                }

                R.id.offers -> {
                    PushFragment(Offers_fragment())
                }

                R.id.shoping -> {
                    PushFragment(Shoping_fragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        dataBinding.appBarRestaurantHome.BottomNavigation.selectedItemId = R.id.restaurant

        handler.post(object : Runnable {
            override fun run() {



                handler.postDelayed(this, 3000)
            }
        })


    }

    fun open_signout() {
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

    fun PushFragment(fragment: Fragment, addtobackstack: Boolean = false) {
        val push = supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
        if (addtobackstack) {
            push.addToBackStack("")//
        }
        push.commit()
    }

    fun getLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Permission is not Granted", Toast.LENGTH_SHORT).show()
            //
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }

        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it != null) {
                val latitude = it.latitude
                val longitude = it.longitude
                val hashmap = hashMapOf(
                    "latitude" to latitude,
                    "longitude" to longitude
                )
                Firebase.firestore.collection(Collection_name_customer).document(currentuser!!)
                    .update(hashmap as Map<String, Any>)
            } else {
                val intent = Intent(this, set_Location::class.java)
                startActivity(intent)


            }
        }
    }

    private val handler = Handler()
    private fun updateLocation() {
        handler.post(object : Runnable {
            override fun run() {
                getLocation()
                handler.postDelayed(this, 10 * 1000) // Repeat every 10 seconds
            }
        })
    }

    override fun onStart() {
        super.onStart()
        updateLocation()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }


}


