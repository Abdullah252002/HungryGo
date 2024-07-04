package com.example.hungrygo.app.home.delivery

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.ProfileDeliveryBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage

class Profile_Delivery : AppCompatActivity() {
    lateinit var dataBinding: ProfileDeliveryBinding
    val data_profile = DataUtils.appuser_Delivery
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.profile_delivery)
        dataBinding.back.setOnClickListener {
            onBackPressed()
        }
        dataBinding.restaurantName.text = data_profile?.name
        dataBinding.phoneNumber.text = data_profile?.mobile_number
        dataBinding.email.text = data_profile?.email

        dataBinding.imageProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1000)
        }

        if (data_profile?.image != null) {
            Firebase.firestore.collection(appUser_delivery.Collection_name_delivery).document(data_profile.id!!)
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
                Firebase.storage.reference.child("${data_profile?.id ?: "unknown"}/photo profile.jpg")
            val uploadImage = storage.putFile(data?.data!!)

            uploadImage
                .addOnSuccessListener {
                    storage.downloadUrl.addOnSuccessListener { uri ->
                        val hash = hashMapOf(
                            "image" to uri.toString()
                        )
                        Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                            .document(data_profile?.id!!).update(hash as Map<String, Any>)
                    }

                }


        }
    }
}