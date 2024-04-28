package com.example.hungrygo

import android.net.Uri
import com.example.hungrygo.app.home.customer.addroom.Room
import com.example.hungrygo.app.model.Message
import com.example.hungrygo.app.model.Room_data
import com.example.hungrygo.app.model.Room_data.Companion.collection_name
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.app.model.appUser_customer.Companion.Collection_name_customer
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_delivery.Companion.Collection_name_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
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
        .reference.child("${id ?: "unknown"}/image.jpg").downloadUrl.addOnSuccessListener(
            onSuccessListener_image
        )

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

fun add_room_tofirebase(
    room: Room_data,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    val db = Firebase.firestore.collection(Room_data.collection_name)
    val doc = db.document()
    room.id = doc.id
    val set = doc.set(room).addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

}

fun getroom(
    onSuccessListener: OnSuccessListener<QuerySnapshot>,
    onFailureListener: OnFailureListener
) {
    val db = Firebase.firestore.collection(collection_name).get()
        .addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener)
}

fun sendmessage_tofirebase(
    message: Message,
    onSuccessListener: OnSuccessListener<Void>
) {
val db=Firebase.firestore.collection(Room_data.collection_name).document(message.roomId!!).
        collection(Message.collection_name)
    val messageRef=db.document()
    message.id=messageRef.id
    messageRef.set(message).addOnSuccessListener(onSuccessListener)
}

fun getmessage_fromfirebase(roomId:String):CollectionReference{
val collectionRef=Firebase.firestore.collection(Room_data.collection_name).document(roomId)
    .collection(Message.collection_name)
    return collectionRef
}