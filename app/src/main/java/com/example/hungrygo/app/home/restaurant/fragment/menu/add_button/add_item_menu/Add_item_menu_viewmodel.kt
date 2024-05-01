package com.example.hungrygo.app.home.restaurant.fragment.menu.add_button.add_item_menu

import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.chat.Basic.Basic_Viewmodel
import com.example.hungrygo.app.model.Item_Menu
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.storage.storage

class Add_item_menu_viewmodel : Basic_Viewmodel<Navigator>() {
    val food_name = ObservableField<String>()
    val content = ObservableField<String>()
    val price = ObservableField<String>()
    val image = MutableLiveData<Uri>()

    val food_name_error = ObservableField<String>()
    val content_error = ObservableField<String>()
    val price_error = ObservableField<String>()

    val currentUser = Firebase.auth.currentUser?.uid
    val menu_name = MutableLiveData<String>()

    fun Done() {
        if (isvalid()) {
            additem()

        }
    }

    fun additem() {
        showDialog.value = true
        val itemMenu = Item_Menu(
            food_name = food_name.get(),
            content = content.get(),
            price = price.get(),
        )
        itemMenu.setItem_Menu(currentUser!!, menu_name.value!!,image.value!!, itemMenu, OnSuccessListener {
            showDialog.value = false
            navigator?.dismess()
        },
            OnFailureListener {
                showDialog.value = false
                messageLiveData.value = it.localizedMessage
            })
    }

    fun isvalid(): Boolean {
        var valid = true
        if (food_name.get().isNullOrBlank()) {
            food_name_error.set("Enter food name")
            handler(food_name_error)
            valid = false
        }
        if (content.get().isNullOrBlank()) {
            content_error.set("Enter content food")
            handler(content_error)
            valid = false
        }
        if (price.get().isNullOrBlank()) {
            price_error.set("Enter price")
            handler(price_error)
            valid = false
        }
        return valid
    }

    fun handler(error: ObservableField<String>) {
        Handler(Looper.getMainLooper()).postDelayed({ error.set(null) }, 3000)
    }
}