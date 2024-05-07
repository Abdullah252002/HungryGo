package com.example.hungrygo.app.home.restaurant.fragment.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.app.model.Item_request.Companion.delete_delivery_restarant
import com.example.hungrygo.app.model.Item_request.Companion.get_delivery_restarant
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.databinding.FragmentDeliveryBinding
import com.example.hungrygo.login_delivery_tofirestore
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Delivery_fragment : Fragment() {
    lateinit var dataBinding: FragmentDeliveryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_delivery, container, false)
        return dataBinding.root
    }

    val adapterGetDelivery = Adapter_get_delivery(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdata()
        dataBinding.recyclerView.adapter = adapterGetDelivery
        adapterGetDelivery.clickListener = object : Adapter_get_delivery.ClickListener {
            override fun delivery(itemRequest: Item_request) {
                login_delivery_tofirestore(itemRequest.delivery_id!!, OnSuccessListener {
                    val items = it.toObject(appUser_delivery::class.java)
                    navigateToprofile?.navigate(items!!)
                })
            }

            override fun delete(
                itemRequest: Item_request,
                holder: Adapter_get_delivery.Viewholder
            ) {
                delete_delivery_restarant(itemRequest.user_id!!, itemRequest.resturant_id!!,
                    OnSuccessListener {
                        holder.dataBinding.profile.visibility = View.GONE
                        holder.dataBinding.refused.visibility=View.GONE
                        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show()
                    })
            }

        }
    }

    private fun getdata() {
        get_delivery_restarant(Firebase.auth.currentUser!!.uid, OnSuccessListener {
            val items = it.toObjects(Item_request::class.java)
            adapterGetDelivery.setlist(items)
        })
    }

    var navigateToprofile: Navigate_toprofile? = null

    interface Navigate_toprofile {
        fun navigate(appUser_delivery: appUser_delivery)
    }


}