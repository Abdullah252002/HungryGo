package com.example.hungrygo.app.model

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener


data class Item_Menu(
    var id: String? = null,
    val food_name: String? = null,
    val content: String? = null,
    val price: String? = null,
    val EGP: String = "EGP"
) {
    fun setItem_Menu(
        userId: String,
        menuname: String,
        itemMenu: Item_Menu,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {
        val db = Firebase.firestore.collection(Collection_name_restaurant).document(userId)
            .collection(menuname)
        val collectionref = db.document()
        id = collectionref.id
        collectionref.set(itemMenu).addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

}
