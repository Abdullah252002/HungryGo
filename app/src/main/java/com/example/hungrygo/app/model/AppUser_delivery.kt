package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

data class appUser_delivery(
    val id: String? = null,
    val name: String? = null,
    val mobile_number: String? = null,
    val national_ID: String? = null,
    val email: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    val image: String? = null,
    val status: String? = "offline",
    val job: String? = "freelance",
    val control:Boolean?=false
) {
    companion object {
        const val Collection_name_delivery = "Delivery Users"
        fun get_all_delivery(onSuccessListener: EventListener<QuerySnapshot>) {
            Firebase.firestore.collection(Collection_name_delivery).addSnapshotListener(onSuccessListener)
        }
    }
}
