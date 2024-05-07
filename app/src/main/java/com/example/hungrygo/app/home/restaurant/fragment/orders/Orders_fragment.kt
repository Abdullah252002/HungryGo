package com.example.hungrygo.app.home.restaurant.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_request
import com.example.hungrygo.app.model.Item_request.Companion.accept_Item_request
import com.example.hungrygo.app.model.Item_request.Companion.get_Item_request_res
import com.example.hungrygo.app.model.Item_request.Companion.refused_Item_request
import com.example.hungrygo.app.model.appUser_customer
import com.example.hungrygo.databinding.FragmentOrdersBinding
import com.example.hungrygo.login_customer_tofirestore
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Orders_fragment : Fragment() {

    lateinit var dataBinding: FragmentOrdersBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)
        return dataBinding.root
    }

    val adapterOrders = Adapter_orders(null)
    val userId = Firebase.auth.currentUser?.uid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.progress.isVisible=false
        getdata()
        dataBinding.recycleview.adapter = adapterOrders
        adapterOrders.clickOnitemListener = object : Adapter_orders.ClickOnitemListener {

            override fun Click_accept(item: Item_request, holder: Adapter_orders.Viewholder) {
                dataBinding.progress.isVisible = true
                accept_Item_request(item.user_id!!, item.resturant_id!!,item, OnSuccessListener {
                    dataBinding.progress.isVisible = false
                    Toast.makeText(requireContext(), "Accept ✔", Toast.LENGTH_SHORT).show()
                })
            }

            override fun Click_refused(item: Item_request, holder: Adapter_orders.Viewholder) {
                if (item.status == "pending") {
                    refused_Item_request(item.user_id!!, item.resturant_id!!, "Refused",
                        OnSuccessListener {
                            Toast.makeText(requireContext(), "order Cancelled", Toast.LENGTH_SHORT)
                                .show()
                        })
                } else {
                    refused_Item_request(item.user_id!!, item.resturant_id!!, "Done",
                        OnSuccessListener {
                            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show()
                        })
                }
            }

            override fun Click_profile(item: Item_request, holder: Adapter_orders.Viewholder) {
                dataBinding.progress.isVisible = true
                login_customer_tofirestore(item.user_id!!, OnSuccessListener {
                    dataBinding.progress.isVisible = false
                    val item_customer = it.toObject(appUser_customer::class.java)
                    navigateProfile?.click(item_customer!!)
                })
            }

        }

    }

    fun getdata() {
        get_Item_request_res(userId!!, OnSuccessListener {
            val items = it.toObjects(Item_request::class.java)
            adapterOrders.setlist(items)
            updateData?.update()
        })
    }

    var navigateProfile: Navigate_profile? = null

    interface Navigate_profile {
        fun click(appuserCustomer: appUser_customer)
    }

    var updateData: Update_data? = null

    interface Update_data {
        fun update()
    }


}