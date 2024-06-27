package com.example.hungrygo.app.model

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

data class AppUser_manger(
    val id: String? = null,
    val name: String? = null,
    val account_type: String? = null,
) {
   companion object{
       fun login_manger_tofirestore(
           id: String,
           onSuccessListener: OnSuccessListener<DocumentSnapshot>
       ) {
           Firebase.firestore.collection("Manger").document(id).get()
               .addOnSuccessListener(onSuccessListener)
       }
   }
}