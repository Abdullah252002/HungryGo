package com.example.hungrygo.app.model

data class Message(
    val id:String?=null,
    val content:String?=null,
    val datetime:Long?=null,
    val sendername:String?=null,
    val senderId:String?=null,
    val roomId:String?=null
)
