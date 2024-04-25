package com.example.hungrygo.app.signup.delivery

data class appUser_delivery(
    val id:String?=null,
    val name:String?=null,
    val mobile_number:String?=null,
    val national_ID:String?=null,
    val email:String?=null
){
    companion object{
        const val Collection_name_delivery="Delivery Users"
    }
}
