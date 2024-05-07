package com.example.hungrygo.app.home.delivery.fragment.restaurant.order

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.app.model.Item_request.Companion.accept_delivery_request
import com.example.hungrygo.app.model.Item_request.Companion.get_Item_request_del
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.GetOrderBinding
import com.example.hungrygo.login_customer_tofirestore
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Get_order(val item: appUser_restaurant) : BottomSheetDialogFragment() {
    lateinit var dataBinding: GetOrderBinding
    val current_user_id = Firebase.auth.currentUser?.uid
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.get_order, container, false)
        return dataBinding.root
    }

    val adapterGetorder = Adapter_getorder(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.name.text = item.restaurant_name
        dataBinding.number.text = item.restaurant_mobile_number
        getdata()
        dataBinding.recyclerView.adapter = adapterGetorder
        dataBinding.location.setOnClickListener {
            openLocationInGoogleMaps(item.latitude!!, item.longitude!!)
        }
        adapterGetorder.clickListener = object : Adapter_getorder.ClickListener {
            override fun accept(
                itemRequest: Item_request,
                holder: Adapter_getorder.Viewholer
            ) {
                val newObject=itemRequest
                newObject.delivery_id=DataUtils.appuser_Delivery?.id
                accept_delivery_request(
                    itemRequest.user_id!!,
                    itemRequest.resturant_id!!,
                    DataUtils.appuser_Delivery?.id!!,
                    newObject,
                    OnSuccessListener {
                        holder.dataBinding.accept.visibility = View.GONE
                    })//

            }

            override fun location(itemRequest: Item_request, holder: Adapter_getorder.Viewholer) {
                login_customer_tofirestore(itemRequest.user_id!!, OnSuccessListener {
                    val item=it.toObject(appUser_customer::class.java)
                    openLocationInGoogleMaps(item?.latitude!!,item.longitude!!)
                })
            }


        }

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

    fun getdata() {
        get_Item_request_del(item.id!!, OnSuccessListener {
            val list = it.toObjects(Item_request::class.java)
            adapterGetorder.setlist(list)
        })

    }

}