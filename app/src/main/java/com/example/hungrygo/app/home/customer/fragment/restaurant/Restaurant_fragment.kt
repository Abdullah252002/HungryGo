package com.example.hungrygo.app.home.customer.fragment.restaurant

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.app.model.appUser_customer.Companion.Collection_name_customer
import com.example.hungrygo.databinding.FragmentRestaurantBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.app.model.appUser_restaurant.Companion.getusers_res


class Restaurant_fragment() : Fragment() {
    lateinit var dataBinding: FragmentRestaurantBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant, container, false)
        return dataBinding.root
    }

    val handler = Handler()
    val adapterRestaurant=Adapter_restaurant(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     //   getdataa()
        dataBinding.recycleview.adapter=adapterRestaurant





    }

    fun getdataa(){
        val id = Firebase.auth.currentUser?.uid

        getusers_res(OnSuccessListener {
            val item = it.toObjects(appUser_restaurant::class.java)
            handler.post(object : Runnable {
                override fun run() {
                    Firebase.firestore.collection(Collection_name_customer).document(id!!).get()
                        .addOnSuccessListener {
                            val db = it.toObject(appUser_customer::class.java)
                            val lat1 = db?.latitude
                            val lon1 = db?.longitude
                            val m:MutableList<appUser_restaurant> = mutableListOf()
                            for (i in 0 until item.size) {
                                val lat2 = item[i].latitude
                                val lon2 = item[i].longitude
                                if (calc_dis(lat1!!, lon1!!, lat2!!, lon2!!) < 3.5) {
                                    m.add(item[i])
                                }
                            }
                            adapterRestaurant.setlist(m)
                            adapterRestaurant.notifyDataSetChanged()
                            update?.onclick()
                        }
                    handler.postDelayed(this, 30 * 1000)
                }

            })
        })

   }
    var update:Update?=null
    interface Update{
        fun onclick()
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