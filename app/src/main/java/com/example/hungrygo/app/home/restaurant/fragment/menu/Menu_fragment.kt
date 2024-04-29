package com.example.hungrygo.app.home.restaurant.fragment.menu

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.hungrygo.Basic.Basic_fragment
import com.example.hungrygo.R
import com.example.hungrygo.databinding.FragmentMenuBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.google.firebase.auth.auth


class Menu_fragment : Basic_fragment<FragmentMenuBinding, Menu_fragment_viewmodel>(), Navigator {


    override fun initViewmodel(): Menu_fragment_viewmodel {
        return ViewModelProvider(this).get(Menu_fragment_viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_menu
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.buttonaction.setOnClickListener {
            onItemClick?.Onitem(it)
        }
        val currentuser=Firebase.auth.uid

        val db = Firebase.firestore.collection(Collection_name_restaurant).document(currentuser!!).get()
            .addOnSuccessListener {
                val db=it.get("email")
                dataBinding.text.setText(""+db)
            }
    }

     var onItemClick: OnItemClick?=null
    interface OnItemClick {
        fun Onitem(view: View)
    }
}