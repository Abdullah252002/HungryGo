package com.example.hungrygo.app.home.restaurant.fragment.menu.add_button.add_item_menu

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Image_Resturant
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.AddItemMenuFraBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Add_Item_Menu_fra(val item: Image_Resturant) : BottomSheetDialogFragment(), Navigator {
    lateinit var databinding: AddItemMenuFraBinding
    lateinit var viewmodel: Add_item_menu_viewmodel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding =
            DataBindingUtil.inflate(inflater, R.layout.add_item_menu_fra, container, false)
        return databinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProvider(this).get(Add_item_menu_viewmodel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databinding.vm = viewmodel
        viewmodel.navigator = this
        viewmodel.menu_name.value = item.image_name
        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant).document(Firebase.auth.currentUser?.uid!!)
            .get().addOnSuccessListener {
                val db=it.toObject(appUser_restaurant::class.java)
                viewmodel.res_name.value=db?.restaurant_name
            }
        databinding.choose.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1000)
        }

    }

    override fun dismess() {
        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            viewmodel.image.value = data?.data


        }
    }
}