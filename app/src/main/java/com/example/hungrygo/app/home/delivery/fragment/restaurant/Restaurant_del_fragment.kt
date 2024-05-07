package com.example.hungrygo.app.home.delivery.fragment.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.app.model.Item_request.Companion.get_Item_request_del
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.app.model.appUser_restaurant.Companion.getusers_res
import com.example.hungrygo.databinding.FragmentRestaurantDelBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Restaurant_del_fragment:Fragment() {
    lateinit var dataBinding: FragmentRestaurantDelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_del,container,false)
        return dataBinding.root
    }

    val adapterRestaurantDel=Adapter_restaurant_del(null)
    val id=Firebase.auth.currentUser?.uid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdata()
        dataBinding.recyclerView.adapter=adapterRestaurantDel
        adapterRestaurantDel.clickOnItemListener=object :Adapter_restaurant_del.ClickOnItemListener{
            override fun onitem(item: appUser_restaurant, position: Int) {
                navigate_request?.navigate(item)
            }

        }

    }
    var navigate_request:Navigate_request?=null
    interface Navigate_request{
        fun navigate(item: appUser_restaurant)
    }

    fun getdata() {
        getusers_res(OnSuccessListener {
            val item = it.toObjects(appUser_restaurant::class.java)
            Firebase.firestore.collection(appUser_delivery.Collection_name_delivery).document(id!!).get()
                .addOnSuccessListener {
                    val db = it.toObject(appUser_delivery::class.java)
                    val lat1 = db?.latitude
                    val lon1 = db?.longitude
                    val m: MutableList<appUser_restaurant> = mutableListOf()
                    for (i in 0 until item.size){
                        val lat2 = item[i].latitude
                        val lon2 = item[i].longitude
                        if (calc_dis(lat1!!, lon1!!, lat2!!, lon2!!) < 3.5 && item[i].order==true) {
                            m.add(item[i])
                        }
                    }
                    adapterRestaurantDel.setlist(m)
                    adapterRestaurantDel.notifyDataSetChanged()
                    updateListener?.update()

                }
        })


    }
    var updateListener:UpdateListener?=null
    interface UpdateListener {
        fun update()
    }
    fun calc_dis(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val radiusOfEarth = 6371
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return radiusOfEarth * c
    }
}