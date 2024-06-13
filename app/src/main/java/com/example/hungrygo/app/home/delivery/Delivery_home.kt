package com.example.hungrygo.app.home.delivery

import android.Manifest
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.home.delivery.fragment.orders.Orders_delFragment
import com.example.hungrygo.app.home.delivery.fragment.restaurant.Restaurant_delFragment
import com.example.hungrygo.app.home.delivery.fragment.restaurant.orders.get_OrdersFragment
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.map.set_Location
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.DeliveryHomeBinding
import com.example.hungrygo.service.MyForegroundService
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.firestore
import com.yariksoffice.lingver.Lingver

class Delivery_home : AppCompatActivity() {
    lateinit var dataBinding: DeliveryHomeBinding
    var currentuser = Firebase.auth.currentUser?.uid
    private val handler = Handler()
    private var isArabic = true
    var restaurantDelfragment = Restaurant_delFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.delivery_home)
        open_signout()
        dataBinding.appBarRestaurantHome.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.restaurant -> {
                    PushFragment(restaurantDelfragment)
                }

                R.id.orders -> {
                    PushFragment(Orders_delFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

        dataBinding.appBarRestaurantHome.BottomNavigation.selectedItemId = R.id.restaurant

        restaurantDelfragment.navigateToOrders =
            object : Restaurant_delFragment.Navigate_to_orders {
                override fun navigate(item: appUser_restaurant) {
                    val getOrdersfragment = get_OrdersFragment(item)
                    getOrdersfragment.show(supportFragmentManager, "")
                }

            }

        check_all()


    }

    val userid = Firebase.auth.currentUser?.uid
    private fun check_all() {
        appUser_restaurant.getusers_res(EventListener { value, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "Listen failed.", error)
                return@EventListener
            }
            if (value != null) {
                val items = value.toObjects(appUser_restaurant::class.java)
                var newlist = mutableListOf<appUser_restaurant>()
                for (i in items) {
                    if (i.order == true)
                        newlist.add(i)
                }
                if (newlist.isNotEmpty()) {
                    change_badgeDrawable(true, R.id.restaurant)
                } else {
                    change_badgeDrawable(false, R.id.restaurant)
                }


            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        })

        Item_Orders.Get_item_Orders_del(userid!!, EventListener { value, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "Listen failed.", error)
                return@EventListener
            }
            if (value != null) {
                val items = value.toObjects(Item_Orders::class.java)
                if (items.isNotEmpty()) {
                    change_badgeDrawable(true, R.id.orders)
                } else {
                    change_badgeDrawable(false, R.id.orders)
                }


            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        })
    }

    fun open_signout() {
        dataBinding.appBarRestaurantHome.menu.setOnClickListener {
            dataBinding.drawerLayout.open()
        }
        dataBinding.language.setOnClickListener {
            val intent = Intent(this, Delivery_home::class.java)
            startActivity(intent)
            finish()
            if (isArabic) {
                Lingver.getInstance().setLocale(this, "en")
            } else {
                Lingver.getInstance().setLocale(this, "ar")
            }
            recreate()
        }
        dataBinding.signout.setOnClickListener {
            stopService(Intent(this, MyForegroundService::class.java))
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
        }else{
            check_notification()
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
                Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                    .document(currentuser!!)
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
                handler.postDelayed(this, 5 * 1000) // Repeat every 10 seconds
            }
        })
    }

    fun check_notification() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }else{
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            startService(serviceIntent)
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                val serviceIntent = Intent(this, MyForegroundService::class.java)
                startService(serviceIntent)
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    override fun onStart() {
        super.onStart()
     //   check_notification()
        updateLocation()

    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }

    fun change_badgeDrawable(isVisible: Boolean, item: Int) {

        val menuItem: MenuItem? =
            dataBinding.appBarRestaurantHome.BottomNavigation.menu.findItem(item)
        val badgeDrawable = dataBinding.appBarRestaurantHome.BottomNavigation
            .getOrCreateBadge(menuItem?.itemId!!)
        badgeDrawable.isVisible = isVisible
        badgeDrawable.backgroundColor = ContextCompat.getColor(this, R.color.red)
    }

    override fun onResume() {
        super.onResume()
        isArabic = Lingver.getInstance().getLanguage() == "ar"
    }


}