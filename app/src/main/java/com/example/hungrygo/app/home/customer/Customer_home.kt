package com.example.hungrygo.app.home.customer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chat.Basic.Basic_Activity
import com.example.hungrygo.R
import com.example.hungrygo.app.home.customer.addroom.Room
import com.example.hungrygo.app.home.customer.addroom.Room_data
import com.example.hungrygo.app.signup.customer.appUser_customer
import com.example.hungrygo.databinding.CustomerHomeBinding
import com.example.hungrygo.getroom
import com.example.hungrygo.login_customer_tofirestore
import com.google.android.gms.common.data.DataHolder
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class Customer_home : Basic_Activity<CustomerHomeBinding, Customer_home_viewmodel>(), Navigator {
    var adapter = adpter_room(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.vm = viewModel
        viewModel.navigator = this


    }

    override fun initViewmodel(): Customer_home_viewmodel {
        return ViewModelProvider(this).get(Customer_home_viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.customer_home
    }

    override fun getMaterialToolbar(): MaterialToolbar? {
        return null
    }

    override fun navigate_to_addroom() {
        val intent = Intent(this, Room::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        getroom(OnSuccessListener {

            val rooms=it.toObjects(Room_data::class.java)
             adapter = adpter_room(rooms)
            dataBinding.recycleview.adapter=adapter
            adapter.onItemClickLister=object :adpter_room.OnItemClickLister{
                override fun onitem(item: Room_data, position: Int, holder: adpter_room.viewholder) {
                    if(validation(holder,item)){
                        Toast.makeText(this@Customer_home, holder.textbox.editText?.text, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        },
            OnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            })
    }

    fun validation(holder:adpter_room.viewholder,item: Room_data):Boolean{
        var valid=true
        if(holder.textbox.editText?.text.toString()!=item.id){
            holder.textbox.error="incorect password"
            valid=false
        }else{
            holder.textbox.error=null
        }
        return valid

    }

}