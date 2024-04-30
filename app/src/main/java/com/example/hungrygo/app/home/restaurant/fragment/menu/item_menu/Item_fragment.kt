package com.example.hungrygo.app.home.restaurant.fragment.menu.item_menu

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.DetailsItemBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage


class Item_fragment(val item: Image_Resturant):Fragment() {
    lateinit var  dataBinding: DetailsItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.details_item,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.menuName.setText(item.image_name)
        dataBinding.buttonaction.setOnClickListener {
            onItemClick?.Onitem(item)
        }


    }
    var onItemClick: OnItemClick? = null
    interface OnItemClick {
        fun Onitem(item: Image_Resturant)
    }




}