package com.example.hungrygo.app.home.restaurant.fragment.delivery.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.databinding.ProfileDeliveryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class profile(val appuserDelivery: appUser_delivery):BottomSheetDialogFragment() {
    lateinit var dataBinding: ProfileDeliveryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding=DataBindingUtil.inflate(inflater, R.layout.profile_delivery,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.name.text=appuserDelivery.name
        dataBinding.number.text=appuserDelivery.mobile_number
        dataBinding.location.setOnClickListener {
            openLocationInGoogleMaps(appuserDelivery.latitude!!,appuserDelivery.longitude!!)
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

}