package com.example.hungrygo.app.home.restaurant.addphoto

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.hungrygo.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.example.hungrygo.app.model.appUser_restaurant.Companion.Collection_name_restaurant


class AddPhoto : AppCompatActivity() {
    lateinit var photo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_photo)
        photo=findViewById(R.id.photo)
        val choose=findViewById<ImageView>(R.id.clickphoto)
        val button=findViewById<Button>(R.id.done)
        button.setOnClickListener {
            finish()
        }
        choose.setOnClickListener {
            val intent= Intent(Intent.ACTION_PICK)
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val id=intent.getStringExtra("id")
        if(resultCode== RESULT_OK){
            photo.setImageURI(data?.data)
             FirebaseStorage.getInstance()
                .reference.child("${id ?: "unknown"}/photo restaurant.jpg")
                .putFile(data?.data!!)
            val hash= hashMapOf(
                "photo" to true
            )

            Firebase.firestore.collection(Collection_name_restaurant).document(id!!).update(hash as Map<String, Any>)
        }
    }
}