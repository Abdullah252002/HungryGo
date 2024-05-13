package com.example.hungrygo.app.home.restaurant

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.databinding.ProfileRestaurantBinding

class Profile_Restaurant : AppCompatActivity() {
    lateinit var dataBinding: ProfileRestaurantBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding= DataBindingUtil.setContentView(this,R.layout.profile_restaurant)
        val data=DataUtils.appuser_Restaurant
        dataBinding.restaurantName.text=data?.restaurant_name
        dataBinding.phoneNumber.text=data?.restaurant_mobile_number
        dataBinding.email.text=data?.email
        if (data?.image!=null){

        }

    }
}

/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val id=intent.getStringExtra("id")
        if(resultCode== RESULT_OK){
            photo.setImageURI(data?.data)
            //
            val storage = Firebase.storage.reference.child("${id ?: "unknown"}/photo restaurant.jpg")
            val uploadImage = storage.putFile(data?.data!!)

            uploadImage
                .addOnSuccessListener {
                    storage.downloadUrl.addOnSuccessListener{ uri ->
                        val hash= hashMapOf(
                            "photo" to true,
                            "image" to uri.toString()
                        )
                        Firebase.firestore.collection(Collection_name_restaurant).document(id!!).update(hash as Map<String, Any>)
                    }

                }



        }
    }

    /*
    val intent= Intent(Intent.ACTION_PICK)
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1000)
     */
 */