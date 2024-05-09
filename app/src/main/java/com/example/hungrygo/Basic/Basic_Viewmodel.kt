package com.example.chat.Basic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
open class Basic_Viewmodel<N>:ViewModel() {
    var navigator:N?=null
    val messageLiveData=MutableLiveData<String>()
    val showDialog=MutableLiveData<Boolean>()
}