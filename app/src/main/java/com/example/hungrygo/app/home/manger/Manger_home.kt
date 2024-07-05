package com.example.hungrygo.app.home.manger

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.login.Login
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.databinding.MangerHomeBinding
import com.example.hungrygo.service.MyForegroundService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.EventListener
import com.yariksoffice.lingver.Lingver

class Manger_home : AppCompatActivity() {
    lateinit var dataBinding: MangerHomeBinding
    private var isArabic = true
    var adapterManger=Adapter_manger(this,null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.manger_home)
        open_drawerLayout()
        getdata()
        dataBinding.appBarMangerHome.recyclerView.adapter=adapterManger
        adapterManger.onClickListner=object :Adapter_manger.OnClickListner{
            override fun onItemClick(position: Int, item: appUser_delivery) {
                val dialogfragmentProfile=DialogFragment_profile(this@Manger_home,item)
                dialogfragmentProfile.show(supportFragmentManager,"")
            }

        }





    }
    fun getdata(){
        appUser_delivery.get_all_delivery(EventListener { value, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "Listen failed.", error)
                return@EventListener
            }
            if (value != null) {
                val items = value.toObjects(appUser_delivery::class.java)
                adapterManger.setlist(items)
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        })
    }
    fun open_drawerLayout() {

        dataBinding.appBarMangerHome.menu.setOnClickListener {
            dataBinding.drawerLayout.open()
        }
        dataBinding.language.setOnClickListener {
            val intent = Intent(this, Manger_home::class.java)
            startActivity(intent)
            finish()
            if (isArabic) {
                Lingver.getInstance().setLocale(this, "en")
            } else {
                Lingver.getInstance().setLocale(this, "ar")
            }
            recreate()
        }
        dataBinding.signout.setOnClickListener {
            Firebase.auth.signOut()
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            stopService(serviceIntent)
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onResume() {
        super.onResume()
        isArabic = Lingver.getInstance().getLanguage() == "ar"
    }
}