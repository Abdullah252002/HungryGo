package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Image_Resturant(
    val id:String?=null,
    val image_name:String?=null,
    val createdTimestamp: Long? = System.currentTimeMillis()
){
    fun setimage(userid:String,menu_name:TextInputLayout,imageResturant: Image_Resturant){
        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant).document(userid).collection("Image")
            .document(menu_name.editText?.text.toString()).set(imageResturant)
    }
    fun getimage(userid:String,onSuccessListener: OnSuccessListener<QuerySnapshot>){
        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
            .document(userid).collection("Image").get().addOnSuccessListener(onSuccessListener)
    }
}
