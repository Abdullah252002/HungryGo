package com.example.hungrygo.app.model

data class appUser_customer(
    var id: String? = null,
    var name: String? = null,
    var mobile_number: String? = null,
    var email: String? = null,
    var latitude:Double?=null,
    var longitude:Double?=null
   ){
    companion object{
        const val Collection_name_customer="Customer Users"
    }
}
