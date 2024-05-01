package com.example.hungrygo.app.model

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage


data class Item_Menu(
    var id: String? = null,
    val food_name: String? = null,
    val content: String? = null,
    val price: String? = null,
    val image:String?=null
) {
    fun setItem_Menu(
        userId: String,
        menuname: String,
        Image_uri: Uri,
        itemMenu: Item_Menu,
        onSuccessListener: OnSuccessListener<Void>,
        onFailureListener: OnFailureListener
    ) {

        val db =
            Firebase.firestore.collection(Collection_name_restaurant).document(userId)
                .collection(menuname)
        val collectionref = db.document(food_name!!)
        id = collectionref.id
        collectionref.set(itemMenu).addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)

        val storage =
            com.google.firebase.ktx.Firebase.storage.reference.child("${userId ?: "unknown"}/item/${food_name}.jpg")
        val uploadImage = storage.putFile(Image_uri)

        uploadImage
            .addOnSuccessListener {
                storage.downloadUrl.addOnSuccessListener { uri ->
                    val image = uri.toString()
                    val hash = hashMapOf(
                        "image" to image
                    )
                    Firebase.firestore.collection(Collection_name_restaurant).document(userId)
                        .collection(menuname)
                        .document(food_name).update(hash as Map<String, Any>)
                }
            }

    }

    companion object{
        fun getItem_Menu(userId: String, menuname: String,onSuccessListener: OnSuccessListener<QuerySnapshot>){
            Firebase.firestore.collection(Collection_name_restaurant).document(userId).collection(menuname)
                .get().addOnSuccessListener(onSuccessListener)
        }
    }
}
