package com.example.hungrygo.app.signup.customer

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.ObservableField
import com.example.chat.Basic.Basic_Viewmodel
import com.example.hungrygo.addUser_Customer_ToFirestore
import com.example.hungrygo.app.model.appUser_customer
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Customer_signup_Viewmodel : Basic_Viewmodel<Navigator>() {
    val name = ObservableField<String>()
    val mobile_no = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    val name_error = ObservableField<String>()
    val mobile_no_error = ObservableField<String>()
    val email_error = ObservableField<String>()
    val password_error = ObservableField<String>()
    private var auth = Firebase.auth


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
        val user = appUser_customer(
            id = uid,
            name = name.get(),
            mobile_number = mobile_no.get(),
            email = email.get(),
        )
        addUser_Customer_ToFirestore(user,
            OnSuccessListener {
                showDialog.value = false
                navigator?.gotologin()
            },
            OnFailureListener {
                showDialog.value = false
                messageLiveData.value=it.localizedMessage
            })
    }


    fun isvalid(): Boolean {
        var valid = true
        if (name.get().isNullOrBlank()) {
            name_error.set("Enter your Name")
            handler(name_error)
            valid = false
        }
        if (mobile_no.get().isNullOrBlank()) {
            mobile_no_error.set("Enter your Phone number")
            handler(mobile_no_error)
            valid = false
        } else if (mobile_no.get()?.length!! < 11 || mobile_no.get()?.length!! > 11) {
            mobile_no_error.set("phone num should be 11 number")
            handler(mobile_no_error)
            valid = false
        }
        if (email.get().isNullOrBlank()) {
            email_error.set("Enter your Email")
            handler(email_error)
            valid = false
        }
        if (password.get().isNullOrBlank()) {
            password_error.set("Enter your Password")
            handler(password_error)
            valid = false
        } else if (password.get()?.length!! < 8) {
            password_error.set("length less than 8 digits")
            handler(password_error)
            valid = false
        }
        return valid
    }
    fun handler(error:ObservableField<String>){
        Handler(Looper.getMainLooper()).postDelayed({error.set(null)},3000)
    }
}

