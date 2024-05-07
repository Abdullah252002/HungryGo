package com.example.hungrygo.app.home.delivery

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
import com.example.hungrygo.app.home.delivery.fragment.orders.Orders_del_fragment
import com.example.hungrygo.app.home.delivery.fragment.restaurant.Restaurant_del_fragment
import com.example.hungrygo.app.home.delivery.fragment.restaurant.order.Get_order
import com.example.hungrygo.app.home.restaurant.fragment.orders.user.user_profile
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.map.set_Location
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.DeliveryHomeBinding
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Delivery_home : AppCompatActivity() {
    lateinit var dataBinding: DeliveryHomeBinding
    var currentuser: String? = null
    private val handler = Handler()
    val restaurantDelFragment=Restaurant_del_fragment()
    val orders_del_fragment=Orders_del_fragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentuser = Firebase.auth.currentUser?.uid
        dataBinding = DataBindingUtil.setContentView(this, R.layout.delivery_home)
        open_signout()
        dataBinding.appBarRestaurantHome.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.restaurant -> {
                    PushFragment(restaurantDelFragment)
                }

                R.id.orders -> {
                    PushFragment(orders_del_fragment)
                }
            }
            return@setOnItemSelectedListener true
        }
        dataBinding.appBarRestaurantHome.BottomNavigation.selectedItemId = R.id.restaurant
        handler.post(object : Runnable {
            override fun run() {
                restaurantDelFragment.updateListener=object :Restaurant_del_fragment.UpdateListener{
                    override fun update() {
                        restaurantDelFragment.getdata()
                    }

                }
                handler.postDelayed(this, 15 * 1000) // Repeat every 10 seconds
            }
        })
        restaurantDelFragment.navigate_request=object :Restaurant_del_fragment.Navigate_request{
            override fun navigate(item: appUser_restaurant) {
                val getOrder=Get_order(item)
                getOrder.show(supportFragmentManager,"")

            }

        }

        orders_del_fragment.navigateUser=object :Orders_del_fragment.Navigate_user{
            override fun navigate_user(appuserCustomer: appUser_customer) {
                val userProfile=user_profile(appuserCustomer)
                userProfile.show(supportFragmentManager,"")
            }


        }




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
                Firebase.firestore.collection(appUser_delivery.Collection_name_delivery).document(currentuser!!)
                    .update(hashmap as Map<String, Any>)
            } else {
                val intent = Intent(this, set_Location::class.java)
                startActivity(intent)

            }
        }
    }
    private fun updateLocation() {
        handler.post(object : Runnable {
            override fun run() {
                getLocation()
                handler.postDelayed(this, 7 * 1000) // Repeat every 10 seconds
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