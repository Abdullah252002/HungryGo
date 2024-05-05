package com.example.hungrygo.app.home.restaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.home.restaurant.addphoto.AddPhoto
import com.example.hungrygo.app.home.restaurant.fragment.delivery.Delivery_fragment
import com.example.hungrygo.app.home.restaurant.fragment.menu.add_button.Add_Menu_fragment
import com.example.hungrygo.app.home.restaurant.fragment.menu.Menu_fragment
import com.example.hungrygo.app.home.restaurant.fragment.menu.add_button.add_item_menu.Add_Item_Menu_fra
import com.example.hungrygo.app.home.restaurant.fragment.menu.item_menu.Item_fragment
import com.example.hungrygo.app.home.restaurant.fragment.orders.Orders_fragment
import com.example.hungrygo.app.home.restaurant.fragment.orders.user.user_profile
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.RestaurantHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Restaurant_home : AppCompatActivity() {
    val user = DataUtils.appuser_Restaurant
    lateinit var dataBinding: RestaurantHomeBinding
    val menuFragment = Menu_fragment()
    val addFragment = Add_Menu_fragment()
    val ordersFragment=Orders_fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.restaurant_home)
        addphoto()
        dataBinding.appBarRestaurantHome.BottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu -> {
                    PushFragment(menuFragment)
                }
                R.id.orders -> {
                    PushFragment(ordersFragment)
                }
                R.id.delivery -> {
                    PushFragment(Delivery_fragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        dataBinding.appBarRestaurantHome.BottomNavigation.selectedItemId = R.id.menu


        handler.post(object : Runnable {
            override fun run() {
                menuFragment.update = object : Menu_fragment.Update {
                    override fun onclick() {
                        menuFragment.getdata()
                    }
                }
                ordersFragment.updateData=object :Orders_fragment.Update_data{
                    override fun update() {
                        ordersFragment.getdata()
                    }

                }
                handler.postDelayed(this, 5000)
            }
        })

        menuFragment.onItemClick = object : Menu_fragment.OnItemClick {
            override fun Onitem(view: View) {
                addFragment.show(supportFragmentManager, "")

            }
        }


        menuFragment.openItemListener = object : Menu_fragment.OpenItemListener {
            override fun onitemclick(position: Int, item: Image_Resturant) {
                val itemFragment = Item_fragment(item)
                PushFragment(itemFragment, true)
//------------------------------------------------------------------------------------------------------------------
                itemFragment.onItemClick = object : Item_fragment.OnItemClick {
                    override fun Onitem(item: Image_Resturant) {
                        val addAdd_Item_Menu_fra = Add_Item_Menu_fra(item)
                        addAdd_Item_Menu_fra.show(supportFragmentManager, "")
                    }
                }

//---------------------------------------------------------------------------------------------------------------
                handler.post(object : Runnable {
                    override fun run() {
                     itemFragment.update=object :Item_fragment.Update{
                         override fun onclick() {
                             itemFragment.getdata()
                         }

                     }
                        handler.postDelayed(this, 1000)
                    }
                })
            }

        }

        ordersFragment.navigateProfile=object :Orders_fragment.Navigate_profile{
            override fun click(appuserCustomer: appUser_customer) {
                val userProfile=user_profile(appuserCustomer)
                userProfile.show(supportFragmentManager,"")
            }

        }



    }


    fun addphoto() {
        dataBinding.appBarRestaurantHome.menu.setOnClickListener {
            dataBinding.drawerLayout.open()
        }
        dataBinding.signout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        Handler(Looper.getMainLooper()).postDelayed({ checkphoto(user!!) }, 3000)
    }

    fun checkphoto(user: appUser_restaurant) {
        if (user.photo == false) {
            val intent = Intent(this, AddPhoto::class.java)
            intent.putExtra("id", user.id)
            startActivity(intent)
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


}
