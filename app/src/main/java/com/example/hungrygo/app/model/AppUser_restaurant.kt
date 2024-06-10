package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
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
    val image:String?=null,
    val order:Boolean?=false,
    val rate_customer:HashMap<String,Double>?=null,
){
    companion object{
        const val Collection_name_restaurant="Restaurant Users"
        fun getusers_res(listener: EventListener<QuerySnapshot>) {
            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .addSnapshotListener(listener)
        }

    }
}
