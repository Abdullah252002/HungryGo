package com.example.hungrygo.app.model

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.ktx.storage


data class Item_Menu(
    var id: String? = null,
    val food_name: String? = null,
    val content: String? = null,
    val price: Int? = null,
    val image:String?=null,
    val counter:Int?=null
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

        fun add_counter_tofirestore(userId: String, item:String,context: Context){
            Firebase.firestore.collection(appUser_customer.Collection_name_customer).document(userId).collection("Shop")
                .document(item).get().addOnSuccessListener {
                    val item=it.toObject(Item_Menu::class.java)
                    var counter=0
                    counter=item?.counter!!+1
                    val hash= hashMapOf(
                        "counter" to counter
                    )
                    Firebase.firestore.collection(appUser_customer.Collection_name_customer).document(userId).collection("Shop")
                        .document(item.food_name!!).update(hash as Map<String, Any>)
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()

                }
        }

        fun minus_counter_tofirestore(userId: String, item: String,context: Context){
            Firebase.firestore.collection(appUser_customer.Collection_name_customer).document(userId).collection("Shop")
                .document(item).get().addOnSuccessListener {
                    val item=it.toObject(Item_Menu::class.java)
                    var counter=0
                    counter=item?.counter!!-1
                    val hash= hashMapOf(
                        "counter" to counter
                    )
                    if (item.counter==1){
                        Firebase.firestore.collection(appUser_customer.Collection_name_customer).document(userId).collection("Shop")
                            .document(item.food_name!!).delete()
                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                    }else{
                        Firebase.firestore.collection(appUser_customer.Collection_name_customer).document(userId).collection("Shop")
                            .document(item.food_name!!).update(hash as Map<String, Any>)
                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()

                    }

                }
        }

    }
}
