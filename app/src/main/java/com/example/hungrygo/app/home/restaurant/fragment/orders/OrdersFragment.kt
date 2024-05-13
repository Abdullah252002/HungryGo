package com.example.hungrygo.app.home.restaurant.fragment.orders

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.Item_Orders.Companion.Get_item_Orders
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.FragmentOrdersBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.firestore

class OrdersFragment : Fragment() {
    lateinit var dataBinding: FragmentOrdersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)
        return dataBinding.root
    }

    var adapterTest = Adapter_test()
    val userid = Firebase.auth.currentUser?.uid

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = Firebase.database.reference
        dataBinding.buttonaction.setOnClickListener {
            navigation?.navigateTo()
        }


        getdata()
        dataBinding.recyclerview.adapter = adapterTest

    }

    fun getdata() {
        Get_item_Orders(userid!!, EventListener { value, error ->
            if (error != null) {
                Log.e(TAG, "Listen failed.", error)
                return@EventListener
            }
            if (value != null) {
                val items = value.toObjects(Item_Orders::class.java)
                adapterTest.setlist(items)
            } else {
                Log.d(TAG, "Current data: null")
            }
        })
    }

    var navigation: Navigation? = null

    interface Navigation {
        fun navigateTo()
    }

}