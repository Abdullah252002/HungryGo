package com.example.hungrygo.app.home.delivery.fragment.restaurant

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.app.model.appUser_restaurant.Companion.getusers_res
import com.google.firebase.Firebase
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.firestore

class Restaurant_delFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_restaurant_del, container, false)
    }

    lateinit var recyclerView: RecyclerView
    var adapterItemRes = Adapter_item_res(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        getRes()
        recyclerView.adapter = adapterItemRes
        adapterItemRes.clickOnItemListener=object :Adapter_item_res.ClickOnItemListener{
            override fun onItemClick(item: appUser_restaurant) {
                navigateToOrders?.navigate(item)
            }

        }

    }
    var navigateToOrders:Navigate_to_orders?=null
    interface Navigate_to_orders{
        fun navigate(item: appUser_restaurant)
    }

    fun getRes() {
        getusers_res(EventListener { value, error ->
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

                adapterItemRes.setRes(newlist)
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        })
    }
}