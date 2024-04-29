package com.example.hungrygo.app.home.restaurant.fragment.menu

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hungrygo.Basic.Basic_fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Menu_Restaurant
import com.example.hungrygo.databinding.FragmentMenuBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.app.model.Image_Resturant
import com.google.android.gms.tasks.OnSuccessListener


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

    lateinit var viewModel:Menu_fragment_viewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Menu_fragment_viewmodel::class.java)
    }
    var adapterMenu = Adapter_menu()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.buttonaction.setOnClickListener {
            onItemClick?.Onitem(it)
        }
        val currentuser = Firebase.auth.uid
        getmenuname_data(currentuser!!)
        getimage_data(currentuser)
        viewModel.menu_name_list.observe(viewLifecycleOwner, Observer {
            adapterMenu.setlist(it)
        })
        viewModel.image_list.observe(viewLifecycleOwner, Observer {
            adapterMenu.setimage(it)
        })
        dataBinding.recycleview.adapter = adapterMenu
    }



    fun getmenuname_data(currentuser: String) {
        Firebase.firestore.collection(Collection_name_restaurant)
            .document(currentuser).get()
            .addOnSuccessListener {
                val item = it.toObject(appUser_restaurant::class.java)
                viewModel.menu_name_list.value = item?.menu
            }
    }

    fun getimage_data(currentuser: String) {
        val imageUris = mutableListOf<Uri>()
        val imageResturant=Image_Resturant()
        imageResturant.getimage(currentuser,"h", OnSuccessListener {
            val dd=it.toObject(Image_Resturant::class.java)
            val uri = Uri.parse(dd?.id)
            imageUris.add(uri)
            imageUris.add(uri)
            imageUris.add(uri)

        })
        viewModel.image_list.value=imageUris

    }


    var onItemClick: OnItemClick? = null

    interface OnItemClick {
        fun Onitem(view: View)
    }

}
