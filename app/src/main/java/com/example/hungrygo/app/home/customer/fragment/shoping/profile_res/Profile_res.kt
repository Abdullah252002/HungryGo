package com.example.hungrygo.app.home.customer.fragment.shoping.profile_res

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.ProfileResBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import java.io.IOException
import java.util.Locale

class Profile_res(val item: appUser_restaurant?) : BottomSheetDialogFragment() {
    lateinit var dataBinding: ProfileResBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.profile_res, container, false)
        return dataBinding.root
    }

    val current_user = Firebase.auth.currentUser?.uid
    private var isRatingSet = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.name.setText(item?.restaurant_name)
        dataBinding.number.setText(item?.restaurant_mobile_number)
        val address = getAddressFromLocation(item?.latitude!!, item.longitude!!)
        dataBinding.contentAddress.setText(address ?: "Address not found")
        dataBinding.location.setOnClickListener {
            openLocationInGoogleMaps(item.latitude, item.longitude)
        }
        dataBinding.materialRatingBar.rating
        dataBinding.materialRatingBar.setOnRatingChangeListener { ratingBar, rating ->
            if (!isRatingSet) {
                isRatingSet = true
                dataBinding.materialRatingBar.setIsIndicator(true)
                addrating(rating)
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

    private fun getAddressFromLocation(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        return try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

            if (addresses?.isNotEmpty() == true) {
                val address: Address = addresses.get(0)
                val addressStringBuilder = StringBuilder()

                for (i in 0..address.maxAddressLineIndex) {
                    addressStringBuilder.append(address.getAddressLine(i)).append(", ")
                }

                addressStringBuilder.toString()
            } else {
                null
            }
        } catch (e: IOException) {
            Log.e("Geocoding", "Error getting address: ${e.message}")
            null
        }
    }

    fun addrating(rating: Float) {
        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
            .document(item?.id!!)
            .collection("rating").document(current_user!!).set(hashMapOf("rating" to rating))
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Rating added", Toast.LENGTH_SHORT).show()
            }
        Item_request.get_rating(item.id, OnSuccessListener {
            set_rating(it)
        })
    }
    var counter=0
    private fun set_rating(it: QuerySnapshot?) {

        for (i in it!!) {
            val cc=i.get("rating").toString()
            if (cc.toFloat()==0f){
                counter+=0
            }else if(cc.toFloat()>=3f){
                counter+=1
            }else if(cc.toFloat()<3f&&cc.toFloat()>=.5f){
                counter-=1
            }
        }
        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant).document(item?.id!!)
            .update(
                hashMapOf("rating" to counter) as Map<String, Any>
            )
    }




}