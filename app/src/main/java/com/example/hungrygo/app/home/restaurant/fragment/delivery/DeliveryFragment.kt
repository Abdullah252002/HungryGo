package com.example.hungrygo.app.home.restaurant.fragment.delivery

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
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class DeliveryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery, container, false)
    }

    val userid = Firebase.auth.currentUser?.uid
    val adapterDelivery = Adapter_delivery(null)
    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        getdata()
        recyclerView.adapter = adapterDelivery
        adapterDelivery.clickListener = object : Adapter_delivery.ClickOnItemListener {
            override fun onItemClick(itemorders: Item_Orders) {
                Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                    .document(itemorders.delivery_id!!).get().addOnSuccessListener {
                        navigateToDelivery?.navigate(it.toObject(appUser_delivery::class.java)!!,itemorders)
                    }

            }

        }
    }

    fun getdata() {
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

                    adapterDelivery.setlist(items)
                } else {
                    Log.d(ContentValues.TAG, "Current data: null")
                }
            }
    }

    var navigateToDelivery: NavigateToDelivery? = null

    interface NavigateToDelivery {
        fun navigate(appUser_delivery: appUser_delivery,itemOrders: Item_Orders)
    }

}