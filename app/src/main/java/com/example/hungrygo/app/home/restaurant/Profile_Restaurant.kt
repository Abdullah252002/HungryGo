package com.example.hungrygo.app.home.restaurant

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.example.hungrygo.databinding.ProfileRestaurantBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class Profile_Restaurant : AppCompatActivity() {
    lateinit var dataBinding: ProfileRestaurantBinding
    val data_profile = DataUtils.appuser_Restaurant
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.profile_restaurant)
        dataBinding.restaurantName.text = data_profile?.restaurant_name
        dataBinding.phoneNumber.text = data_profile?.restaurant_mobile_number
        dataBinding.email.text = data_profile?.email

        dataBinding.imageProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1000)
        }

        if (data_profile?.image != null) {
            Firebase.firestore.collection(Collection_name_restaurant).document(data_profile.id!!)
                .get().addOnSuccessListener {
                    Glide.with(this).load(it.get("image"))
                        .into(dataBinding.imageProfile)
            }
        } else {
            dataBinding.imageProfile.setImageResource(R.drawable.profile)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            dataBinding.imageProfile.setImageURI(data?.data)
            //
            val storage =
                Firebase.storage.reference.child("${data_profile?.id ?: "unknown"}/photo restaurant.jpg")
            val uploadImage = storage.putFile(data?.data!!)

            uploadImage
                .addOnSuccessListener {
                    storage.downloadUrl.addOnSuccessListener { uri ->
                        val hash = hashMapOf(
                            "image" to uri.toString()
                        )
                        Firebase.firestore.collection(Collection_name_restaurant)
                            .document(data_profile?.id!!).update(hash as Map<String, Any>)
                    }

                }


        }
    }


}



