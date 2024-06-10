package com.example.hungrygo.app.home.restaurant.app_bar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.CustomerEvaluationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Customer_Evaluation : AppCompatActivity() {
    lateinit var dataBinding: CustomerEvaluationBinding
    val adapterCustomerEvaluation = Adapter_customer_evaluation(null)
    val userid = Firebase.auth.currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.customer_evaluation)
        showBackButton()
        getdata()
        dataBinding.recyclerView.adapter = adapterCustomerEvaluation


    }

    fun getdata() {
        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant).document(userid!!).get()
            .addOnSuccessListener {
                if(it.get("rate_customer")is HashMap<*, *>){
                    @Suppress("UNCHECKED_CAST")
                    adapterCustomerEvaluation.setlist(it.get("rate_customer") as HashMap<String, Double>)
                }

            }

    }

    fun showBackButton() {
        setSupportActionBar(dataBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}