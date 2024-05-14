package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

data class Item_Orders(
    var id: String? = null,
    val location: String? = null,
    val price: String? = null,
    var delivery_id: String? = null,
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

        fun Get_item_Orders_res(userid: String, listener: EventListener<QuerySnapshot>) {
            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(userid).collection("Orders")
                .orderBy("createdTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(listener)
        }
        fun Get_item_Orders_del(userid: String, listener: EventListener<QuerySnapshot>) {
            Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                .document(userid).collection("Orders")
                .orderBy("createdTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(listener)
        }

        fun update_item_Orders(userid: String, status: Boolean) {
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

        fun accept_order(
            delivery_id: String,
            id_item: String,
            restaurant_id: String,
            itemOrders: Item_Orders,
            onSuccessListener: OnSuccessListener<Void>,
            onFailureListener: OnFailureListener
        ) {
            val db = Firebase.firestore
            val delivery = db.collection(appUser_delivery.Collection_name_delivery)
                .document(delivery_id).collection("Orders").document(id_item)
            val restaurant_add = db.collection(appUser_restaurant.Collection_name_restaurant)
                .document(restaurant_id).collection("Delivery").document(id_item)
            val restaurant_remove = db.collection(appUser_restaurant.Collection_name_restaurant)
                .document(restaurant_id).collection("Orders").document(id_item)
            db.runBatch { batch ->
                batch.set(delivery, itemOrders)
                batch.set(restaurant_add, itemOrders)
                batch.delete(restaurant_remove)
            }.addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
        }
    }
}
