package com.example.hungrygo.app.home.customer.addroom


data class Room_data(
    var id:String?=null,
    val name:String?=null,
    val details: String?=null
){
    companion object{
        val collection_name="Room"
    }
}
