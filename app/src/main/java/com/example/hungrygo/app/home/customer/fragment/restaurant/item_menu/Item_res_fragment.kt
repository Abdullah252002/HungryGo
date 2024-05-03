package com.example.hungrygo.app.home.customer.fragment.restaurant.item_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.app.model.Item_Menu
import com.google.android.gms.tasks.OnSuccessListener

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
        recyclerView.adapter=adapterItemRes
        getdata()

        adapterItemRes.clickOnItemListener=object :Adapter_item_res.ClickOnItemListener{
            override fun onitem(item: Item_Menu, position: Int) {
               addItemShoping?.onclick(true)
            }

        }


    }
    fun getdata(){
        Item_Menu.getItem_Menu(id_res!!, item.image_name!!, OnSuccessListener {
            val items = it.toObjects(Item_Menu::class.java)
                adapterItemRes.setlist(items)//
        })
    }

    var addItemShoping:Add_item_shoping?=null
    interface Add_item_shoping{
        fun onclick(isVisible:Boolean)
    }
}