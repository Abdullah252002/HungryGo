package com.example.hungrygo.app.signup.restaurant

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chat.Basic.Basic_Viewmodel
import com.example.hungrygo.addUser_restaurant_ToFirestore
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Restaurant_signup_Viewmodel : Basic_Viewmodel<Navigator>() {
    val manager_name = ObservableField<String>()
    val restaurant_name = ObservableField<String>()
    val manager_mobile_no = ObservableField<String>()
    val restaurant_mobile_no = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val latitude=MutableLiveData<Double>()
    val longitude=MutableLiveData<Double>()


    val manager_name_error = ObservableField<String>()
    val restaurant_name_error = ObservableField<String>()
    val manager_mobile_no_error = ObservableField<String>()
    val restaurant_mobile_no_error = ObservableField<String>()
    val email_error = ObservableField<String>()
    val password_error = ObservableField<String>()
    val location_error=MutableLiveData<String>()

    private val auth = Firebase.auth

    fun navigate_location(){
        navigator?.setlocation()
    }

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
        val user = appUser_restaurant(
            id = uid,
            manager_name=manager_name.get(),
            restaurant_name=restaurant_name.get(),
            manager_mobile_number = manager_mobile_no.get(),
            restaurant_mobile_number=restaurant_mobile_no.get(),
            email = email.get(),
            latitude=latitude.value,
            longitude=longitude.value
        )
        addUser_restaurant_ToFirestore(user,
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
        if (manager_name.get().isNullOrBlank()) {
            manager_name_error.set("Enter your Name")
            valid = false
        } else {
            manager_name_error.set(null)
        }
        if (restaurant_name.get().isNullOrBlank()) {
            restaurant_name_error.set("Enter your Name")
            valid = false
        } else {
            restaurant_name_error.set(null)
        }
        if (manager_mobile_no.get().isNullOrBlank()) {
            manager_mobile_no_error.set("Enter your Phone number")
            valid = false
        } else if (manager_mobile_no.get()?.length!! < 11 || manager_mobile_no.get()?.length!! > 11) {
            manager_mobile_no_error.set("phone num should be 11 number")
            valid=false
        } else {
            manager_mobile_no_error.set(null)
        }
        if (restaurant_mobile_no.get().isNullOrBlank()) {
            restaurant_mobile_no_error.set("Enter your Phone number")
            valid = false
        } else if (restaurant_mobile_no.get()?.length!! < 11 || restaurant_mobile_no.get()?.length!! > 11) {
            restaurant_mobile_no_error.set("phone num should be 11 number")
            valid=false
        } else {
            restaurant_mobile_no_error.set(null)
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
        if(latitude.value==null || longitude.value==null){
            location_error.value="set location"
            valid = false
        }else{
            location_error.value=""
        }

        return valid
    }

}