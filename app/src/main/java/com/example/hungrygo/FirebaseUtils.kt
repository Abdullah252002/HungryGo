package com.example.hungrygo

import android.net.Uri
import com.example.hungrygo.app.signup.customer.appUser_customer
import com.example.hungrygo.app.signup.customer.appUser_customer.Companion.Collection_name_customer
import com.example.hungrygo.app.signup.delivery.appUser_delivery
import com.example.hungrygo.app.signup.delivery.appUser_delivery.Companion.Collection_name_delivery
import com.example.hungrygo.app.signup.restaurant.appUser_restaurant
import com.example.hungrygo.app.signup.restaurant.appUser_restaurant.Companion.Collection_name_restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

fun addUser_Customer_ToFirestore(
    user: appUser_customer,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val db = Firebase.firestore.collection(Collection_name_customer).document(user.id!!).set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun addUser_delivery_ToFirestore(
    user: appUser_delivery,
    imageUri: Uri,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener

) {
    val storage = FirebaseStorage.getInstance()
        .reference.child("${user.id ?: "unknown"}/image.jpg")
        .putFile(imageUri)

    val db = Firebase.firestore.collection(Collection_name_delivery).document(user.id!!).set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

}

fun addUser_restaurant_ToFirestore(
    user: appUser_restaurant,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val db = Firebase.firestore.collection(Collection_name_restaurant).document(user.id!!).set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)


}

fun login_customer_tofirestore(
    id: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>
) {
    val db = Firebase.firestore.collection(Collection_name_customer)
        .document(id).get()
        .addOnSuccessListener(onSuccessListener)

}

fun login_delivery_tofirestore(
    id: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onSuccessListener_image: OnSuccessListener<Uri>
) {
    val storage = FirebaseStorage.getInstance()
        .reference.child("${id ?: "unknown"}/image.jpg").downloadUrl.addOnSuccessListener(onSuccessListener_image)

    val db = Firebase.firestore.collection(Collection_name_delivery)
        .document(id).get()
        .addOnSuccessListener(onSuccessListener)
}

fun login_resturant_tofirestore(
    id: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
) {
    val db = Firebase.firestore.collection(Collection_name_restaurant)
        .document(id).get()
        .addOnSuccessListener(onSuccessListener)
}
