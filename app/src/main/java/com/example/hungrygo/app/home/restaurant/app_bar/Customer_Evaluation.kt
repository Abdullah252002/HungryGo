package com.example.hungrygo.app.home.restaurant.app_bar

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
        hideProgressBar()
        showBackButton()
        getdata()
        dataBinding.recyclerView.adapter = adapterCustomerEvaluation
        adapterCustomerEvaluation.onClickListener = object : Adapter_customer_evaluation.OnClickListener {
                override fun onClick(item: String?, rating: Float) {
                    showProgressBar()
                    Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                        .document(userid!!).get().addOnSuccessListener {
                            val hashMap =
                                it.get("rate_customer") as? HashMap<String, String> ?: hashMapOf()
                            hashMap[item!!] = rating.toString()
                            Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                                .document(userid).update("rate_customer", hashMap)
                            hideProgressBar()
                        }.addOnFailureListener {
                            hideProgressBar()
                        }
                }

            }


    }

    fun getdata() {
        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
            .document(userid!!).get()
            .addOnSuccessListener {
                if (it.get("rate_customer") is HashMap<*, *>) {
                    @Suppress("UNCHECKED_CAST")
                    adapterCustomerEvaluation.setlist(it.get("rate_customer") as HashMap<String, String>)
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


    fun showProgressBar() {
        val progressOverlay = findViewById<View>(R.id.progress_overlay)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressOverlay.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
    }


    fun hideProgressBar() {
        val progressOverlay = findViewById<View>(R.id.progress_overlay)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressOverlay.visibility = View.GONE
        progressBar.visibility = View.GONE
    }


}