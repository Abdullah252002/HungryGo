package com.example.hungrygo.app.model

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
     const val collection_name="Room"
    }

}
