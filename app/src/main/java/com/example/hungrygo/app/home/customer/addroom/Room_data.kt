package com.example.hungrygo.app.home.customer.addroom

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room_data(
    var id:String?=null,
    val name:String?=null,
    val details: String?=null
):Parcelable{
    companion object{
        val collection_name="Room"
    }

}
