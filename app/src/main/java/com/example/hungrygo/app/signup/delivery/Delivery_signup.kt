package com.example.hungrygo.app.signup.delivery

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.databinding.DeliverySignupBinding
import com.google.android.material.appbar.MaterialToolbar

class Delivery_signup : Basic_Activity<DeliverySignupBinding, Delivery_signup_Viewmodel>(),
    Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm=viewModel
        viewModel.navigator=this
        dataBinding.upload.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1000)
        }
        viewModel.image_error.observe(this, Observer {
            dataBinding.errorImage.setTextColor(Color.RED)
            dataBinding.errorImage.setText(it)
        })


    }

    override fun initViewmodel(): Delivery_signup_Viewmodel {
        return ViewModelProvider(this).get(Delivery_signup_Viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.delivery_signup
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return dataBinding.toolbar
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
           dataBinding.uploadImage.setImageURI(data?.data)
            viewModel.image.value=data?.data
           //   val uri = Uri.parse(dd)  convert string to uri


        }
    }

    override fun gotologin() {
        onBackPressed()
    }
}