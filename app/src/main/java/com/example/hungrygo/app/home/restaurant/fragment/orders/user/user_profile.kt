package com.example.hungrygo.app.home.restaurant.fragment.orders.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.databinding.UserProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class user_profile(val appuserCustomer: appUser_customer) : BottomSheetDialogFragment() {
    lateinit var dataBinding: UserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding=DataBindingUtil.inflate(inflater, R.layout.user_profile,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.name.setText(appuserCustomer.name)
        dataBinding.number.setText(appuserCustomer.mobile_number)
        dataBinding.number.setOnClickListener {
            val phoneNumber =appuserCustomer.mobile_number
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)

        }
        dataBinding.location.setOnClickListener {
            openLocationInGoogleMaps(appuserCustomer.latitude!!,appuserCustomer.longitude!!)
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