package com.example.hungrygo.app.home.restaurant.fragment.menu.addmenu

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hungrygo.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant
import com.google.firebase.firestore.FieldValue
import com.example.hungrygo.app.model.Image_Resturant
import com.google.firebase.storage.ktx.storage


class Add_fragment: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_fragment,container,false)
    }

    lateinit var photo: ImageView
    lateinit var textView: TextView
    lateinit var done:Button
    lateinit var menu_name:TextInputLayout
    var userid:String?=null
    var image:Uri?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menu_name=view.findViewById(R.id.menu_name)
        done=view.findViewById(R.id.done)
        userid=Firebase.auth.currentUser?.uid
        photo=view.findViewById(R.id.photo)
        textView=view.findViewById(R.id.upload)
        val choose=view.findViewById<ImageView>(R.id.clickphoto)

        choose.setOnClickListener {
            val intent= Intent(Intent.ACTION_PICK)
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1000)
        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== AppCompatActivity.RESULT_OK){
               image=data?.data!!
            photo.setImageURI(data.data)
        }
    }

    override fun onResume() {
        super.onResume()
        done.setOnClickListener {
            if(image!=null){
            if (menu_name.editText?.text.isNullOrBlank()) {
                menu_name.error = "Enter Menu name"
            } else {
                val hashMap= hashMapOf(
                    "menu" to FieldValue.arrayUnion(menu_name.editText?.text.toString())
                )
                Firebase.firestore.collection(Collection_name_restaurant).document(userid!!).update(hashMap as Map<String, Any>)

                // upload the user selected image
                val storage = Firebase.storage.reference.child("${userid ?: "unknown"}/Menu/${menu_name.editText?.text}.jpg")
                val uploadImage = storage.putFile(image!!)

                uploadImage
                    .addOnSuccessListener {
                        storage.downloadUrl.addOnSuccessListener{ uri ->
                            val dd=Image_Resturant(id=uri.toString(), image_name =menu_name.editText?.text.toString())
                            dd.setimage(userid!!,menu_name,dd)
                                 }

                    }

                dismiss()
            }
        }else{
            textView.setTextColor(Color.RED)
            textView.setText("upload image")
        }


        }

    }

}