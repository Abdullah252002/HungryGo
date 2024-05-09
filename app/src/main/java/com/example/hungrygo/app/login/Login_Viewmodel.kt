package com.example.hungrygo.app.login

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.ObservableField
import com.example.chat.Basic.Basic_Viewmodel
import com.example.hungrygo.DataUtils
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.login_customer_tofirestore
import com.example.hungrygo.login_delivery_tofirestore
import com.example.hungrygo.login_resturant_tofirestore
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Login_Viewmodel : Basic_Viewmodel<Navigator>() {
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    val email_error = ObservableField<String>()
    val password_error = ObservableField<String>()

    private var auth = Firebase.auth
    fun navigate_to_signup() {
        navigator?.navigate_to_signup()
    }

    fun login() {
        if (isvalid()) {
            goto_account()
        }
    }

    fun goto_account() {
        showDialog.value = true
        auth.signInWithEmailAndPassword(email.get()!!, password.get()!!).addOnCompleteListener {
            if (it.isSuccessful) {
                showDialog.value = false
                Log.e("firebase", "success")
                messageLiveData.value = "Successful Login"
                getdata(it.result.user?.uid)
            } else {
                showDialog.value = false
                Log.e("firebase", it.exception?.localizedMessage!!)
                password_error.set("Email or Password is not correct")
                email_error.set("Email or Password is not correct")
                handler(password_error)
                handler(email_error)
            }

        }
    }

    private fun getdata(uid: String?) {
        login_customer_tofirestore(uid!!,
            OnSuccessListener {
                val user = it.toObject(appUser_customer::class.java)
                if (user != null) {
                    DataUtils.appUser_customer=user
                    navigator?.navigate_customer_home()
                }
            })
        login_delivery_tofirestore(uid!!,
            OnSuccessListener {
              val user=it.toObject(appUser_delivery::class.java)
              if(user!=null){
                  DataUtils.appuser_Delivery=user
                  navigator?.navigate_delivery_home()
              }
            })
        login_resturant_tofirestore(uid!!,
            OnSuccessListener {
                val user=it.toObject(appUser_restaurant::class.java)
                if(user!=null){
                    DataUtils.appuser_Restaurant=user
                    navigator?.navigate_restaurant_home()
                }
            })

    }


    fun isvalid(): Boolean {
        var valid = true
        if (email.get().isNullOrBlank()) {
            email_error.set("Enter your Email")
            handler(email_error)
            valid = false
        }
        if (password.get().isNullOrBlank()) {
            password_error.set("Enter your Password")
            handler(password_error)
            valid = false
        }
        return valid
    }
    fun handler(error:ObservableField<String>){
        Handler(Looper.getMainLooper()).postDelayed({error.set(null)},3000)
    }
}