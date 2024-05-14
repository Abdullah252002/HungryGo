package com.example.hungrygo.app.home.delivery.fragment.orders

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.Item_Orders.Companion.Get_item_Orders_del
import com.example.hungrygo.app.model.appUser_delivery
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.firestore


class Orders_delFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders_del, container, false)
    }

    val userid = Firebase.auth.currentUser?.uid
    val adapterGetOrders2 = Adapter_get_orders2(null)
    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        get_data()
        recyclerView.adapter = adapterGetOrders2
        adapterGetOrders2.clickListener = object : Adapter_get_orders2.ClickOnItemListener {
            override fun onItemClick(itemorders: Item_Orders) {
                Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                    .document(userid!!).collection("Orders").document(itemorders.id!!)
                    .delete().addOnSuccessListener {
                        Toast.makeText(requireContext(), "Order Deleted", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "Order Not Deleted", Toast.LENGTH_SHORT).show()
                    }
            }

        }
    }

    fun get_data() {
        Get_item_Orders_del(userid!!, EventListener { value, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "Listen failed.", error)
                return@EventListener
            }
            if (value != null) {
                val items = value.toObjects(Item_Orders::class.java)

                adapterGetOrders2.setlist(items)
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        })
    }

}