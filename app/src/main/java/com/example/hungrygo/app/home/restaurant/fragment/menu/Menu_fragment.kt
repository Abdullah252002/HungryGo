package com.example.hungrygo.app.home.restaurant.fragment.menu

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hungrygo.R
import com.example.hungrygo.databinding.FragmentMenuBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.hungrygo.app.model.Image_Resturant
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentChange


lateinit var  dataBinding:FragmentMenuBinding
class Menu_fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_menu,container,false)
        return dataBinding.root
    }

    var adapterMenu = Adapter_menu()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.buttonaction.setOnClickListener {
            onItemClick?.Onitem(it)
        }
        getdata()
        dataBinding.recycleview.adapter = adapterMenu
        adapterMenu.clickOnItemListener=object :Adapter_menu.ClickOnItemListener{
            override fun onitem(position: Int, item: Image_Resturant) {
                openItemListener?.onitemclick(position,item)
            }
        }
    }


fun getdata(){
    val currentuser = Firebase.auth.uid
    val imageResturant=Image_Resturant()
    imageResturant.getimage(userid = currentuser!!, onSuccessListener = OnSuccessListener {
        val items=it.toObjects(Image_Resturant::class.java)
        adapterMenu.setitems(items)
        adapterMenu.notifyDataSetChanged()
        clickButtonListener?.onclick()
    })
}

    var openItemListener:OpenItemListener?=null
    interface OpenItemListener{
        fun onitemclick(position: Int, item: Image_Resturant)
    }

    var onItemClick: OnItemClick? = null
    interface OnItemClick {
        fun Onitem(view: View)
    }

    var clickButtonListener:ClickButtonListener?=null
    interface ClickButtonListener{
        fun onclick()
    }

}
