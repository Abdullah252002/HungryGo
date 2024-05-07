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
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.app.model.Item_request.Companion.accept_delivery_request
import com.example.hungrygo.app.model.Item_request.Companion.get_Item_request_del
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.GetOrderBinding
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
            override fun onItemClicked(
                itemRequest: Item_request,
                holder: Adapter_getorder.Viewholer
            ) {
                accept_delivery_request(
                    itemRequest.user_id!!,
                    itemRequest.resturant_id!!,
                    current_user_id!!,
                    itemRequest,
                    OnSuccessListener {
                        holder.dataBinding.accept.visibility = View.GONE
                    })//

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