package com.example.hungrygo.app.home.restaurant.fragment.menu.add_button.add_item_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.databinding.AddItemMenuFraBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Add_Item_Menu_fra(val item: Image_Resturant): BottomSheetDialogFragment(),Navigator {
    lateinit var databinding: AddItemMenuFraBinding
    lateinit var viewmodel: Add_item_menu_viewmodel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding= DataBindingUtil.inflate(inflater,R.layout.add_item_menu_fra,container,false)
        return databinding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel=ViewModelProvider(this).get(Add_item_menu_viewmodel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databinding.vm=viewmodel
        viewmodel.navigator=this
        viewmodel.menu_name.value=item.image_name


    }

    override fun dismess() {
        dismiss()
    }
}