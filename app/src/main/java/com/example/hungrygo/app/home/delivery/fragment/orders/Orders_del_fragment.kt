package com.example.hungrygo.app.home.delivery.fragment.orders

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.app.model.Item_request.Companion.delete_delivery_request
import com.example.hungrygo.app.model.Item_request.Companion.get_request_for_delivery
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.FragmentOrdersDelBinding
import com.example.hungrygo.databinding.FragmentRestaurantDelBinding
import com.example.hungrygo.login_customer_tofirestore
import com.example.hungrygo.login_resturant_tofirestore
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Orders_del_fragment : Fragment() {
    lateinit var dataBinding: FragmentOrdersDelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_orders_del, container, false)
        return dataBinding.root
    }

    val adapterOrdersDel = Adapter_orders_del(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getdata()
        dataBinding.recyclerView.adapter = adapterOrdersDel
        adapterOrdersDel.clickListener = object : Adapter_orders_del.ClickListener {
            override fun cus_location(item: Item_request) {
                login_customer_tofirestore(item.user_id!!, OnSuccessListener {
                    val db = it.toObject(appUser_customer::class.java)
                    navigateUser?.navigate_user(db!!)

                })
            }

            override fun res_location(item: Item_request) {
                login_resturant_tofirestore(item.resturant_id!!, OnSuccessListener {
                    val db = it.toObject(appUser_restaurant::class.java)
                    openLocationInGoogleMaps(db?.latitude!!, db.longitude!!)
                })

            }

            override fun delete(item: Item_request, holder: Adapter_orders_del.Viewholer) {
                delete_delivery_request(item.user_id!!,item.delivery_id!!, OnSuccessListener {
                    holder.dataBinding.accept.visibility=View.GONE
                    holder.dataBinding.profile.visibility=View.GONE
                    holder.dataBinding.refused.visibility=View.GONE
                    Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT).show()
                })
            }


        }



    }

    private fun getdata() {
        get_request_for_delivery(DataUtils.appuser_Delivery?.id!!, OnSuccessListener {
            val list = it.toObjects(Item_request::class.java)
            adapterOrdersDel.setlist(list)
        })
    }

    private fun openLocationInGoogleMaps(lat: Double, lng: Double) {
        val label = "My Location"
        val uri = "geo:$lat,$lng?q=$lat,$lng($label)"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            val mapUrl = "https://www.google.com/maps/search/?api=1&query=$lat,$lng"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl)))
        }
    }

    var navigateUser: Navigate_user? = null

    interface Navigate_user {
        fun navigate_user(appuserCustomer: appUser_customer)
    }

}