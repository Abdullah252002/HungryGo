package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

data class appUser_restaurant(
    val id:String?=null,
    val manager_name:String?=null,
    val restaurant_name:String?=null,
    val manager_mobile_number:String?=null,
    val restaurant_mobile_number:String?=null,
    val email:String?=null,
    val latitude:Double?=null,
    val longitude:Double?=null,
    val photo:Boolean?=false,
    val image:String?=null,
    val order:Boolean?=false
){
    companion object{
        const val Collection_name_restaurant="Restaurant Users"
        fun getusers_res(onSuccessListener: OnSuccessListener<QuerySnapshot>){
            Firebase.firestore.collection(Collection_name_restaurant).get().addOnSuccessListener(onSuccessListener)
        }

    }
}
