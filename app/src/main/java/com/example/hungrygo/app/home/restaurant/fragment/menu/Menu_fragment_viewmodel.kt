package com.example.hungrygo.app.home.restaurant.fragment.menu

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.chat.Basic.Basic_Viewmodel
import com.google.firebase.firestore.FieldValue

class Menu_fragment_viewmodel:Basic_Viewmodel<Navigator>() {

    val menu_name_list=MutableLiveData<List<String>>()
    var image_list=MutableLiveData<List<Uri>>()





}