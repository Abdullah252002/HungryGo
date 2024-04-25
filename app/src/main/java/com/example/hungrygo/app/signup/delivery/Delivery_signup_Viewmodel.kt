package com.example.hungrygo.app.signup.delivery

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chat.Basic.Basic_Viewmodel
import com.example.hungrygo.addUser_delivery_ToFirestore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Delivery_signup_Viewmodel : Basic_Viewmodel<Navigator>() {
    val name = ObservableField<String>()
    val mobile_no = ObservableField<String>()
    val national_ID = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val image = MutableLiveData<Uri>()
    private var auth = Firebase.auth


    val name_error = ObservableField<String>()
    val mobile_no_error = ObservableField<String>()
    val national_ID_error = ObservableField<String>()
    val email_error = ObservableField<String>()
    val password_error = ObservableField<String>()
    val image_error = MutableLiveData<String>()


    fun signup() {
        if (isvalid()) {
            createaccount()
        }
    }

    fun createaccount() {
        showDialog.value = true
        auth.createUserWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("firebase", "success")
                    showDialog.value = false
                    messageLiveData.value = "Successful Register"
                    storedata(task.result.user?.uid)

                } else {
                    showDialog.value = false
                    Log.e("firebase", task.exception?.localizedMessage!!)
                    email_error.set(task.exception?.localizedMessage!!)
                }
            }
    }

    private fun storedata(uid: String?) {
        showDialog.value = true
        val user = appUser_delivery(
            id = uid,
            name = name.get(),
            mobile_number = mobile_no.get(),
            national_ID = national_ID.get(),
            email = email.get()
        )
        addUser_delivery_ToFirestore(user, image.value!!,
            OnSuccessListener {
                showDialog.value = false
                navigator?.gotologin()
            }, OnFailureListener {
                showDialog.value = false
                messageLiveData.value=it.localizedMessage
            })
    }

    fun isvalid(): Boolean {
        var valid = true
        if (name.get().isNullOrBlank()) {
            name_error.set("Enter your Name")
            valid = false
        } else {
            name_error.set(null)
        }
        if (mobile_no.get().isNullOrBlank()) {
            mobile_no_error.set("Enter your Phone number")
            valid = false
        } else if (mobile_no.get()?.length!! < 11 || mobile_no.get()?.length!! > 11) {
            mobile_no_error.set("phone should be 11 number")
            valid = false
        } else {
            mobile_no_error.set(null)
        }
        if (national_ID.get().isNullOrBlank()) {
            national_ID_error.set("Enter your National ID")
            valid = false
        } else if (national_ID.get()?.length!! < 14 || national_ID.get()?.length!! > 14) {
            national_ID_error.set("National ID should be 14 number")
            valid = false
        } else {
            national_ID_error.set(null)
        }
        if (email.get().isNullOrBlank()) {
            email_error.set("Enter your Email")
            valid = false
        } else {
            email_error.set(null)
        }
        if (password.get().isNullOrBlank()) {
            password_error.set("Enter your Password")
            valid = false
        } else if (password.get()?.length!! < 8) {
            password_error.set("length less than 8 digits")
            valid = false
        } else {
            password_error.set(null)
        }
        if (image.value == null) {
            image_error.value = "image is empty"
            valid = false
        } else {
            image_error.value = ""
        }
        return valid
    }
}

