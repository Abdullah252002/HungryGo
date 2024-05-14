package com.example.hungrygo.app.home.delivery.fragment.restaurant.orders

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.Item_Orders.Companion.Get_item_Orders_res
import com.example.hungrygo.app.model.Item_Orders.Companion.accept_order
import com.example.hungrygo.app.model.appUser_restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.EventListener


class get_OrdersFragment(val item: appUser_restaurant) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_get__orders, container, false)
    }

    lateinit var recyclerView: RecyclerView
    val adapterGetOrders=Adapter_get_orders(null)
    val userid=Firebase.auth.currentUser?.uid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.recyclerView)
        get_data()
        recyclerView.adapter=adapterGetOrders
        adapterGetOrders.clickListener=object :Adapter_get_orders.ClickOnItemListener{
            override fun onItemClick(itemorders: Item_Orders) {
                val newitem=itemorders
                newitem.delivery_id=userid!!
                accept_order(userid,itemorders.id!!,item.id!!,newitem, OnSuccessListener {
                    Toast.makeText(requireContext(),"Order Accepted",Toast.LENGTH_SHORT).show()
                }, OnFailureListener {
                    Toast.makeText(requireContext(),"Order Not Accepted",Toast.LENGTH_SHORT).show()
                })
            }

        }

    }

   fun get_data(){
       Get_item_Orders_res(item.id!!, EventListener { value, error ->
           if (error != null) {
               Log.e(ContentValues.TAG, "Listen failed.", error)
               return@EventListener
           }
           if (value != null) {
               val items = value.toObjects(Item_Orders::class.java)
               adapterGetOrders.setlist(items)
           } else {
               Log.d(ContentValues.TAG, "Current data: null")
           }
       })
    }


}