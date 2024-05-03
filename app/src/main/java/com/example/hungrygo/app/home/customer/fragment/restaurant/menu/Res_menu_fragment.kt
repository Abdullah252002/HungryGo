package com.example.hungrygo.app.home.customer.fragment.restaurant.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.app.model.appUser_restaurant
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Res_menu_fragment(val item: appUser_restaurant) :Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_res_menu,container,false)
    }
    lateinit var recyclerView: RecyclerView
    val adapterMenuRes=Adapter_menu_res()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.recycleview)
        getdata()
        recyclerView.adapter=adapterMenuRes


    }
    fun getdata(){
        val id_res = item.id
        val imageResturant= Image_Resturant()
        imageResturant.getimage(userid = id_res!!, onSuccessListener = OnSuccessListener {
            val items=it.toObjects(Image_Resturant::class.java)
            adapterMenuRes.setitems(items)
            adapterMenuRes.notifyDataSetChanged()

        })
    }
}