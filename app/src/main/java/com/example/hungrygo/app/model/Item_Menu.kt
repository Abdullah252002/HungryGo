package com.example.hungrygo.app.model

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage


data class Item_Menu(
    var id: String? = null,
    val food_name: String? = null,
    val content: String? = null,
    val price: String? = null,
    val EGP: String = "EGP",
    var image: String? = null
) {
    fun setItem_Menu(
        userId: String,
        menuname: String,
        Image_uri: Uri,
        itemMenu: Item_Menu,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {
        val storage =
            com.google.firebase.ktx.Firebase.storage.reference.child("${userId ?: "unknown"}/item/${food_name}.jpg")
        val uploadImage = storage.putFile(Image_uri)

        uploadImage
            .addOnSuccessListener {
                storage.downloadUrl.addOnSuccessListener { uri ->
                    image = uri.toString()
                    val db =
                        Firebase.firestore.collection(Collection_name_restaurant).document(userId)
                            .collection(menuname)
                    val collectionref = db.document()
                    id = collectionref.id
                    collectionref.set(itemMenu).addOnSuccessListener(onSuccessListener)
                        .addOnFailureListener(onFailureListener)

                }


            }

    }
}
