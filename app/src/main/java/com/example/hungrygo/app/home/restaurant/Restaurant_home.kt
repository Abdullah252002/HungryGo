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
import com.example.hungrygo.app.home.restaurant.fragment.delivery.DeliveryFragment
import com.example.hungrygo.app.home.restaurant.fragment.delivery.profile.Profile_deliveryFragment
import com.example.hungrygo.app.home.restaurant.fragment.orders.add.Add_item_delFragment
import com.example.hungrygo.app.home.restaurant.fragment.orders.OrdersFragment
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.databinding.RestaurantHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.yariksoffice.lingver.Lingver

class Restaurant_home : AppCompatActivity() {
    private var isArabic = true
    val user = DataUtils.appuser_Restaurant
    lateinit var dataBinding: RestaurantHomeBinding
    var ordersFragment = OrdersFragment()
    val addItemDelfragment = Add_item_delFragment()
    val deliveryFragment = DeliveryFragment()


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

        deliveryFragment.navigateToDelivery=object :DeliveryFragment.NavigateToDelivery{
            override fun navigate(appUser_delivery: appUser_delivery, itemOrders: Item_Orders) {
                val profileDeliveryfragment=Profile_deliveryFragment(appUser_delivery,itemOrders)
                profileDeliveryfragment.show(supportFragmentManager,"")
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
