package com.example.hungrygo

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
@BindingAdapter("app:error")
fun seterror(textInputLayout: TextInputLayout,error:String?){
    textInputLayout.error=error
}
