package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

data class Item_Orders(
    var id: String? = null,
    val location: String? = null,
    val price: String? = null,
    val createdTimestamp: Long? = System.currentTimeMillis()
) {
    companion object {
        fun Add_item_Orders(
            userid: String,
            itemOrders: Item_Orders,
            onSuccessListener: OnSuccessListener<Void>,
            onFailureListener: OnFailureListener
        ) {
            val db = Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(userid).collection("Orders").document()
            itemOrders.id = db.id
            db.set(itemOrders).addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
        }

        fun Get_item_Orders(userid: String,listener:EventListener<QuerySnapshot>) {
            val db = Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(userid).collection("Orders").addSnapshotListener(listener)
        }
    }
}
