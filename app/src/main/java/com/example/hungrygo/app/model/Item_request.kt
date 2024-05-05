package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

data class Item_request(
    val user_id: String? = null,
    val resturant_id: String? = null,
    val resturant_name:String?=null,
    val list_item: String? = null,
    val total_price: Int? = null,
    val status: String = "pending"
) {
    fun delete_shoping(user_id: String) {
        Firebase.firestore.collection(appUser_customer.Collection_name_customer)
            .document(user_id).collection("Shop").get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    Firebase.firestore.collection(appUser_customer.Collection_name_customer)
                        .document(user_id).collection("Shop")
                        .document(document.id).delete()
                }

            }
    }



    fun set_Item_request(
        user_id: String,
        resturant_id: String,
        itemRequest: Item_request,
        onSuccessListener: OnSuccessListener<Void>
    ) {
        val db = Firebase.firestore
        val Customer = db.collection(appUser_customer.Collection_name_customer).document(user_id)
            .collection("Request").document(resturant_id)
        val Restaurant =
            db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                .collection("Request").document(user_id)
        db.runBatch { batch ->
            batch.set(Customer, itemRequest)
            batch.set(Restaurant, itemRequest)
        }.addOnSuccessListener(onSuccessListener)

    }

    companion object {
        fun get_Item_request(user_id: String,onSuccessListener: OnSuccessListener<QuerySnapshot>) {
            Firebase.firestore.collection(appUser_customer.Collection_name_customer).document(user_id)
                .collection("Request").get().addOnSuccessListener(onSuccessListener)
        }
        fun delete_shoping_total(user_id: String, resturant_id: String,onSuccessListener:OnSuccessListener<Void>){
            val db = Firebase.firestore
            val Customer = db.collection(appUser_customer.Collection_name_customer).document(user_id)
                .collection("Request").document(resturant_id)
            val Restaurant =
                db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                    .collection("Request").document(user_id)
            db.runBatch { batch ->
                batch.delete(Customer)
                batch.delete(Restaurant)
            }.addOnSuccessListener(onSuccessListener)

        }

    }


}
