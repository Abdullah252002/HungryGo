package com.example.hungrygo.app.home.restaurant.fragment.delivery.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.FragmentProfileDeliveryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class Profile_deliveryFragment(
    val appUser_delivery: appUser_delivery,
    val itemOrders: Item_Orders
) : BottomSheetDialogFragment() {

    lateinit var dataBinding: FragmentProfileDeliveryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_delivery, container, false)
        return dataBinding.root
    }

    val userid = Firebase.auth.currentUser?.uid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.name.text = appUser_delivery.name
        dataBinding.number.text = appUser_delivery.mobile_number
        dataBinding.location.setOnClickListener {
            openLocationInGoogleMaps(appUser_delivery.latitude!!, appUser_delivery.longitude!!)
        }
        dataBinding.delete.setOnClickListener {
            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(userid!!).collection("Delivery").document(itemOrders.id!!)
                .delete().addOnSuccessListener {
                    Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                    dismiss()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
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
}