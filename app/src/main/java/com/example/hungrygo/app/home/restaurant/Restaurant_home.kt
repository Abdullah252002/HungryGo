package com.example.hungrygo.app.home.restaurant

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.home.restaurant.fragment.delivery.DeliveryFragment
import com.example.hungrygo.app.home.restaurant.fragment.delivery.profile.Profile_deliveryFragment
import com.example.hungrygo.app.home.restaurant.fragment.orders.add.Add_item_delFragment
import com.example.hungrygo.app.home.restaurant.fragment.orders.OrdersFragment
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.service.MyForegroundService
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.RestaurantHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.yariksoffice.lingver.Lingver

class Restaurant_home : AppCompatActivity() {
    private var isArabic = true
    val user = DataUtils.appuser_Restaurant
    lateinit var dataBinding: RestaurantHomeBinding
    var ordersFragment = OrdersFragment()
    val addItemDelfragment = Add_item_delFragment()
    val deliveryFragment = DeliveryFragment()
    val userid = Firebase.auth.currentUser?.uid

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.restaurant_home)
        open_drawerLayout()

        dataBinding.appBarRestaurantHome.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.orders -> {
                    PushFragment(ordersFragment)
                }

                R.id.delivery -> {
                    PushFragment(deliveryFragment)
                }
            }
            return@setOnItemSelectedListener true
        }
        dataBinding.appBarRestaurantHome.BottomNavigation.selectedItemId = R.id.orders

        ordersFragment.navigation = object : OrdersFragment.Navigation {
            override fun navigateTo() {
                addItemDelfragment.show(supportFragmentManager, "")
            }

        }

        deliveryFragment.navigateToDelivery = object : DeliveryFragment.NavigateToDelivery {
            override fun navigate(appUser_delivery: appUser_delivery, itemOrders: Item_Orders) {
                val profileDeliveryfragment = Profile_deliveryFragment(appUser_delivery, itemOrders)
                profileDeliveryfragment.show(supportFragmentManager, "")
            }
        }

        check_all_drawerLayout()

    }


    private fun check_all_drawerLayout() {
        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
            .document(userid!!).collection("Delivery")
            .orderBy("createdTimestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->

                if (error != null) {
                    Log.e(ContentValues.TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }
                if (value != null) {
                    val items = value.toObjects(Item_Orders::class.java)
                    if (items.isNotEmpty()) {
                        change_badgeDrawable(true, R.id.delivery)
                    } else {
                        change_badgeDrawable(false, R.id.delivery)
                    }

                } else {
                    Log.d(ContentValues.TAG, "Current data: null")
                }
            }
    }

    fun open_drawerLayout() {
        dataBinding.profile.setOnClickListener {
            startActivity(Intent(this, Profile_Restaurant::class.java))
        }
        dataBinding.appBarRestaurantHome.menu.setOnClickListener {
            dataBinding.drawerLayout.open()
        }
        dataBinding.language.setOnClickListener {
            val intent = Intent(this, Restaurant_home::class.java)
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
            Firebase.auth.signOut()
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            stopService(serviceIntent)
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

    private val handler = Handler()

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
