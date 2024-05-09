package com.example.hungrygo.app.home.customer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.home.customer.fragment.restaurant.Restaurant_fragment
import com.example.hungrygo.app.home.customer.fragment.restaurant.item_menu.Item_res_fragment
import com.example.hungrygo.app.home.customer.fragment.restaurant.menu.Res_menu_fragment
import com.example.hungrygo.app.home.customer.fragment.shoping.Shoping_fragment
import com.example.hungrygo.app.home.customer.fragment.shoping.profile_res.Profile_res
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.map.set_Location
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.databinding.CustomerHomeBinding
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.example.hungrygo.app.model.appUser_customer.Companion.Collection_name_customer
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.get_item_into_shoping
import com.google.android.gms.tasks.OnSuccessListener

class Customer_home : AppCompatActivity() {
    lateinit var dataBinding: CustomerHomeBinding
    var currentuser: String? = null
    val restaurantFragment = Restaurant_fragment()
    val shopingFragment = Shoping_fragment()
    private val handler = Handler()
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

                R.id.shoping -> {
                    PushFragment(shopingFragment)
                    change_badgeDrawable(false)
                }
            }
            return@setOnItemSelectedListener true
        }
        dataBinding.appBarRestaurantHome.BottomNavigation.selectedItemId = R.id.restaurant

        restaurantFragment.navigateToMenu = object : Restaurant_fragment.Navigate_to_menu {
            override fun navigate(item: appUser_restaurant) {
                val resMenuFragment = Res_menu_fragment(item)
                PushFragment(resMenuFragment, true)

                resMenuFragment.navigateToitemmenu =
                    object : Res_menu_fragment.Navigate_toitemmenu {
                        override fun navigate(item: Image_Resturant, id_res: String?) {
                            val itemResFragment = Item_res_fragment(item, id_res)
                            PushFragment(itemResFragment, true)

                            itemResFragment.addItemShoping =
                                object : Item_res_fragment.Add_item_shoping {
                                    override fun onclick(isVisible: Boolean) {
                                        change_badgeDrawable(isVisible)
                                    }

                                }
                        }

                    }

            }

        }

        handler.post(object : Runnable {
            override fun run() {
                shopingFragment.updateData=object :Shoping_fragment.Update_data{
                    override fun update() {
                        shopingFragment.getdata()
                    }
                    override fun update_total() {
                        shopingFragment.getdata_total()
                    }
                }
                handler.postDelayed(this, 7 * 1000)
            }
        })

        shopingFragment.profileResturant=object :Shoping_fragment.Profile_resturant{
            override fun profile(item: appUser_restaurant?) {
                val profileRes= Profile_res(item)
                profileRes.show(supportFragmentManager,"")
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
                Firebase.firestore.collection(Collection_name_customer).document(currentuser!!)
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

    fun change_badgeDrawable(isVisible: Boolean) {


        val menuItem: MenuItem? =
            dataBinding.appBarRestaurantHome.BottomNavigation.menu.findItem(R.id.shoping)
        val badgeDrawable = dataBinding.appBarRestaurantHome.BottomNavigation
            .getOrCreateBadge(menuItem?.itemId!!)
        badgeDrawable.isVisible = isVisible
        get_item_into_shoping(Firebase.auth.currentUser?.uid!!, OnSuccessListener {
            badgeDrawable.number = it.size()
        })
        badgeDrawable.backgroundColor = ContextCompat.getColor(this, R.color.red)
    }

}


