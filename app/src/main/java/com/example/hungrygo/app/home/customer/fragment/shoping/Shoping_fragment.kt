package com.example.hungrygo.app.home.customer.fragment.shoping

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Menu
import com.example.hungrygo.app.model.Item_Menu.Companion.add_counter_tofirestore
import com.example.hungrygo.app.model.Item_Menu.Companion.minus_counter_tofirestore
import com.example.hungrygo.databinding.FragmentShopingBinding
import com.example.hungrygo.get_item_into_shoping
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Shoping_fragment : Fragment() {
    lateinit var dataBinding: FragmentShopingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoping, container, false)
        return dataBinding.root
    }

    val adapterShoping = Adapter_shoping(null)
    val handler = Handler()
    val userid = Firebase.auth.currentUser?.uid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getdata()

        dataBinding.recycleview.adapter = adapterShoping
        adapterShoping.add = object : Adapter_shoping.ClickOnAdd {
            override fun add_counter(item: Item_Menu) {
                add_counter_tofirestore(userid!!, item.food_name!!,requireContext())
            }
        }
        adapterShoping.minus=object :Adapter_shoping.ClickOnMinus{
            override fun minus_counter(item: Item_Menu) {
                minus_counter_tofirestore(userid!!,item.food_name!!,requireContext())
            }

        }

    }

    fun show_visibility() {
        dataBinding.shop.visibility = View.VISIBLE
        dataBinding.total.visibility = View.VISIBLE
        dataBinding.egp.visibility = View.VISIBLE
        dataBinding.price.visibility = View.VISIBLE
    }

    fun hide_visibility() {
        dataBinding.shop.visibility = View.GONE
        dataBinding.total.visibility = View.GONE
        dataBinding.egp.visibility = View.GONE
        dataBinding.price.visibility = View.GONE
    }

    fun getdata() {

        get_item_into_shoping(userid!!, OnSuccessListener {
            val item = it.toObjects(Item_Menu::class.java)
            var counter = 0
            for (i in 0 until item.size) {
                counter += item[i].price!!*item[i]!!.counter!!
            }
            if (item.size != 0) {
                show_visibility()
            }
            dataBinding.price.setText("" + counter)
            adapterShoping.setlist(item)
            updateData?.update()
        })
    }

    fun Setshoping() {
        dataBinding.shop.setOnClickListener {
            hide_visibility()
        }
    }

    var updateData: Update_data? = null

    interface Update_data {
        fun update()
    }

}