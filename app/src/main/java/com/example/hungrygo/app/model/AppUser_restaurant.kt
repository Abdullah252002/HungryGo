package com.example.hungrygo.app.model

data class appUser_restaurant(
    val id:String?=null,
    val manager_name:String?=null,
    val restaurant_name:String?=null,
    val manager_mobile_number:String?=null,
    val restaurant_mobile_number:String?=null,
    val email:String?=null,
    val latitude:Double?=null,
    val longitude:Double?=null,
    val photo:Boolean?=false
){
    companion object{
        const val Collection_name_restaurant="Restaurant Users"
    }
}
