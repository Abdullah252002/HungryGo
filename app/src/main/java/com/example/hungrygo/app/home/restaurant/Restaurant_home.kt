package com.example.hungrygo.app.home.restaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.databinding.RestaurantHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Restaurant_home : AppCompatActivity() {
    val user = DataUtils.appuser_Restaurant
    lateinit var dataBinding: RestaurantHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.restaurant_home)
        open_drawerLayout()
        /*
                dataBinding.appBarRestaurantHome.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> {
                    PushFragment(menuFragment)
                }

                R.id.orders -> {
                    PushFragment(ordersFragment)
                }

                R.id.delivery -> {
                    PushFragment(deliveryFragment)
                }
            }
            return@setOnItemSelectedListener true
        }

         */
        dataBinding.appBarRestaurantHome.BottomNavigation.selectedItemId = R.id.menu






    }


    fun open_drawerLayout() {
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

    private val handler = Handler()

    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }


    fun change_badgeDrawable(isVisible: Boolean,item:Int) {

        val menuItem: MenuItem? =
            dataBinding.appBarRestaurantHome.BottomNavigation.menu.findItem(item)
        val badgeDrawable = dataBinding.appBarRestaurantHome.BottomNavigation
            .getOrCreateBadge(menuItem?.itemId!!)
        badgeDrawable.isVisible = isVisible
        badgeDrawable.backgroundColor = ContextCompat.getColor(this, R.color.red)
    }


}
