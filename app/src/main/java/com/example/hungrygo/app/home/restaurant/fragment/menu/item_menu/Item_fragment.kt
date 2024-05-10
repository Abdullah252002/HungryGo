package com.example.hungrygo.app.home.restaurant.fragment.menu.item_menu

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.app.model.Item_Menu
import com.example.hungrygo.app.model.Item_Menu.Companion.getItem_Menu
import com.example.hungrygo.databinding.DetailsItemBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


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


    val adapterItem=Adapter_item(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.menuName.setText(item.image_name)
        dataBinding.buttonaction.setOnClickListener {
            onItemClick?.Onitem(item)
        }
        getdata()
        dataBinding.recycleview.adapter=adapterItem
    }

    fun getdata(){
        val currentuser=Firebase.auth.currentUser?.uid
        getItem_Menu(currentuser!!,item.image_name!!, OnSuccessListener {
            val items=it.toObjects(Item_Menu::class.java)
            adapterItem.setlist(items)
            update?.onclick()
        })
    }

    var onItemClick: OnItemClick? = null
    interface OnItemClick {
        fun Onitem(item: Image_Resturant)
    }

    var update:Update?=null
    interface Update{
        fun onclick()
    }


}