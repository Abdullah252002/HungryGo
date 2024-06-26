package com.example.hungrygo.app.home.customer.fragment.restaurant.item_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.add_item_into_shoping
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.app.model.Item_Menu
import com.example.hungrygo.get_item_into_shoping
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Item_res_fragment(val item: Image_Resturant,val id_res: String?) :Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detalis_item_res,container,false)
    }

    val adapterItemRes= Adapter_item_res(null)
   lateinit var recyclerView:RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.recycle)
        val currentuser=Firebase.auth.currentUser?.uid
        recyclerView.adapter=adapterItemRes
        getdata()

        adapterItemRes.clickOnItemListener=object :Adapter_item_res.ClickOnItemListener{
            override fun onitem(item: Item_Menu, position: Int) {
                get_item_into_shoping(currentuser!!, OnSuccessListener {
                    val items=it.toObjects(Item_Menu::class.java)
                    if (items.size==0||items[0].id==item.id){
                        add_item_into_shoping(currentuser,item.food_name!!,item, OnSuccessListener {
                            addItemShoping?.onclick(true)
                            Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                        })
                    }else{
                        Toast.makeText(requireContext(), "Cannot add items from another restaurant", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }


    }
    fun getdata(){
        Item_Menu.getItem_Menu(id_res!!, item.image_name!!, OnSuccessListener {
            val items = it.toObjects(Item_Menu::class.java)
                adapterItemRes.setlist(items)
        })
    }

    var addItemShoping:Add_item_shoping?=null
    interface Add_item_shoping{
        fun onclick(isVisible:Boolean)
    }
}