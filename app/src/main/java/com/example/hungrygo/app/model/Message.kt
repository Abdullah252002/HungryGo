package com.example.hungrygo.app.model

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.SimpleTimeZone

data class Message(
    var id:String?=null,
    val content:String?=null,
    val datetime:Long?=null,
    val sendername:String?=null,
    val senderId:String?=null,
    val roomId:String?=null,
    val createdTimestamp: Long? = System.currentTimeMillis()
){
    companion object{
      const val collection_name="Chat"
    }
    fun date():String{
        val date=Date(datetime!!)
      val simpledate=  SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpledate.format(date)
    }
}
