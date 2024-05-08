package com.example.hungrygo.app.home.customer.fragment.shoping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Menu
import com.example.hungrygo.app.model.Item_Menu.Companion.add_counter_tofirestore
import com.example.hungrygo.app.model.Item_Menu.Companion.minus_counter_tofirestore
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.app.model.Item_request.Companion.delete_shoping_total
import com.example.hungrygo.app.model.Item_request.Companion.get_Item_request
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.FragmentShopingBinding
import com.example.hungrygo.get_item_into_shoping
import com.example.hungrygo.login_resturant_tofirestore
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
    val adapterShpoingTotal = Adapter_shpoing_total(null)
    val userid = Firebase.auth.currentUser?.uid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getdata()
        getdata_total()

        dataBinding.recycleview.adapter = adapterShoping
        dataBinding.recycleShoping.adapter = adapterShpoingTotal
        adapterShoping.add = object : Adapter_shoping.ClickOnAdd {
            override fun add_counter(item: Item_Menu) {
                add_counter_tofirestore(userid!!, item.food_name!!, requireContext())
            }
        }
        adapterShoping.minus = object : Adapter_shoping.ClickOnMinus {
            override fun minus_counter(item: Item_Menu) {
                minus_counter_tofirestore(userid!!, item.food_name!!, requireContext())
            }

        }
        adapterShpoingTotal.clickOnitemListener=object :Adapter_shpoing_total.ClickOnitemListener{
            override fun onitem(item: Item_request) {
                delete_shoping_total(item.user_id!!,item.resturant_id!!, OnSuccessListener {
                    Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show()
                })
            }

            override fun button(item: Item_request) {
                login_resturant_tofirestore(item.resturant_id!!, OnSuccessListener {
                    val db=it.toObject(appUser_restaurant::class.java)
                    profileResturant?.profile(db)
                })

            }

        }


    }

    fun getdata_total() {
        get_Item_request(userid!!, OnSuccessListener {
            val items = it.toObjects(Item_request::class.java)
            if (items.size==0){
                dataBinding.recycleShoping.visibility=View.GONE
            }
            dataBinding.recycleShoping.visibility=View.VISIBLE
            adapterShpoingTotal.setlist(items)
            updateData?.update_total()
        })

    }

    fun visibility(view: Int) {
        dataBinding.shop.visibility = view
        dataBinding.total.visibility = view
        dataBinding.egp.visibility = view
        dataBinding.price.visibility = view
    }

    fun getdata() {

        get_item_into_shoping(userid!!, OnSuccessListener {
            val items = it.toObjects(Item_Menu::class.java)
            var counter = 0
            for (i in 0 until items.size) {
                counter += items[i].price!! * items[i]!!.counter!!
            }
            if (items.size != 0) {
                visibility(View.VISIBLE)
            } else {
                visibility(View.GONE)
            }
            dataBinding.price.setText("" + counter)
            adapterShoping.setlist(items)
            Request(counter, items)
            updateData?.update()
        })
    }

    fun Request(counter: Int, items: MutableList<Item_Menu>) {
        dataBinding.shop.setOnClickListener {
            var list_item = ""
            for (i in 0 until items.size) {
                list_item += items[i].counter.toString() + " " + items[i].food_name +if (i!=items.size-1){"\n"} else {""}

            }
            val itemRequest = Item_request(
                user_id = userid, resturant_id = items[0].id,items[0].resturant_name!!,
                list_item = list_item ,total_price = counter
            )
            itemRequest.set_Item_request(userid!!, items[0].id!!, itemRequest,
                OnSuccessListener {
                    itemRequest.delete_shoping(userid)

                })
        }
    }



    var updateData: Update_data? = null

    interface Update_data {
        fun update()
        fun update_total()
    }

    var profileResturant:Profile_resturant?=null
    interface Profile_resturant{
        fun profile(item: appUser_restaurant?)
    }

}