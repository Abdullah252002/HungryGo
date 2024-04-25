package com.example.hungrygo.app.home.customer

import com.example.chat.Basic.Basic_Viewmodel

class Customer_home_viewmodel:Basic_Viewmodel<Navigator>() {


    fun navigatetoaddroom(){
        navigator?.navigate_to_addroom()
    }
}