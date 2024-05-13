package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
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

        fun Get_item_Orders(userid: String, listener: EventListener<QuerySnapshot>) {
            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(userid).collection("Orders")
                .orderBy("createdTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(listener)
        }
        fun update_item_Orders(userid: String,status:Boolean){
            val hashMap = hashMapOf(
                "order" to status
            )
            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(userid).update(hashMap as Map<String, Any>)

        }

        fun Delete_item_Orders(
            userid: String,
            id: String,
            onSuccessListener: OnSuccessListener<Void>,
            onFailureListener: OnFailureListener
        ) {
            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(userid).collection("Orders").document(id).delete()
                .addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
        }
    }
}
