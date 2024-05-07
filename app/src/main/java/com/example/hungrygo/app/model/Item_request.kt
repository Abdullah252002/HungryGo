package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import io.grpc.okhttp.internal.proxy.Request

data class Item_request(
    val user_id: String? = null,
    val resturant_id: String? = null,
    val resturant_name: String? = null,
    val list_item: String? = null,
    val total_price: Int? = null,
    val status: String = "pending",
    var delivery_id: String? = null
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
        fun get_Item_request(user_id: String, onSuccessListener: OnSuccessListener<QuerySnapshot>) {
            Firebase.firestore.collection(appUser_customer.Collection_name_customer)
                .document(user_id)
                .collection("Request").get().addOnSuccessListener(onSuccessListener)
        }

        fun get_Item_request_res(
            user_id: String,
            onSuccessListener: OnSuccessListener<QuerySnapshot>
        ) {
            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(user_id)
                .collection("Request").get().addOnSuccessListener(onSuccessListener)
        }

        fun get_Item_request_del(
            user_id: String,
            onSuccessListener: OnSuccessListener<QuerySnapshot>
        ) {
            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                .document(user_id)
                .collection("Accept").get().addOnSuccessListener(onSuccessListener)
        }

        fun delete_shoping_total(
            user_id: String,
            resturant_id: String,
            onSuccessListener: OnSuccessListener<Void>
        ) {
            val db = Firebase.firestore
            val Customer =
                db.collection(appUser_customer.Collection_name_customer).document(user_id)
                    .collection("Request").document(resturant_id)
            val Restaurant =
                db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                    .collection("Request").document(user_id)
            db.runBatch { batch ->
                batch.delete(Customer)
                batch.delete(Restaurant)
            }.addOnSuccessListener(onSuccessListener)

        }

        fun accept_Item_request(
            user_id: String,
            resturant_id: String,
            itemRequest: Item_request,
            onSuccessListener: OnSuccessListener<Void>
        ) {
            val db = Firebase.firestore
            val Customer =
                db.collection(appUser_customer.Collection_name_customer).document(user_id)
                    .collection("Request").document(resturant_id)
            val Restaurant =
                db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                    .collection("Request").document(user_id)
            val Restaurant_accept =
                db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                    .collection("Accept").document(user_id)
            val accept = hashMapOf(
                "status" to "Accept"
            )
            db.runBatch { batch ->
                batch.update(Customer, accept as Map<String, Any>)
                batch.update(Restaurant, accept as Map<String, Any>)
                batch.set(Restaurant_accept, itemRequest)
            }.addOnSuccessListener(onSuccessListener)

        }

        fun refused_Item_request(
            user_id: String,
            resturant_id: String,
            status: String,
            onSuccessListener: OnSuccessListener<Void>
        ) {
            val db = Firebase.firestore
            val Customer =
                db.collection(appUser_customer.Collection_name_customer).document(user_id)
                    .collection("Request").document(resturant_id)
            val Restaurant =
                db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                    .collection("Request").document(user_id)
            val accept = hashMapOf(
                "status" to status
            )
            db.runBatch { batch ->
                batch.update(Customer, accept as Map<String, Any>)
                batch.delete(Restaurant)
            }.addOnSuccessListener(onSuccessListener)

        }

        fun accept_delivery_request(
            user_id: String,
            resturant_id: String,
            delivery_id: String,
            itemRequest: Item_request,
            onSuccessListener: OnSuccessListener<Void>
        ) {
            val db = Firebase.firestore
            val Restaurant_req =
                db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                    .collection("Request").document(user_id)
            val Restaurant_acc =
                db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                    .collection("Accept").document(user_id)
            val Restaurant_del =
                db.collection(appUser_restaurant.Collection_name_restaurant).document(resturant_id)
                    .collection("Delivery").document(user_id)
            val Delivery =
                db.collection(appUser_delivery.Collection_name_delivery).document(delivery_id)
                    .collection("Request").document(user_id)

            db.runBatch { batch ->
                batch.delete(Restaurant_req)
                batch.delete(Restaurant_acc)
                batch.set(Restaurant_del, itemRequest)
                batch.set(Delivery, itemRequest)
            }.addOnSuccessListener(onSuccessListener)

        }


        fun get_request_for_delivery(
            delivery_id: String,
            onSuccessListener: OnSuccessListener<QuerySnapshot>
        ) {
            val db = Firebase.firestore
            db.collection(appUser_delivery.Collection_name_delivery).document(delivery_id)
                .collection("Request").get().addOnSuccessListener(onSuccessListener)

        }

        fun delete_delivery_request(
            user_id: String,
            delivery_id: String,
            onSuccessListener: OnSuccessListener<Void>
        ) {
            val db = Firebase.firestore
            db.collection(appUser_delivery.Collection_name_delivery).document(delivery_id)
                .collection("Request").document(user_id).delete().addOnSuccessListener(onSuccessListener)
        }

    }


}
