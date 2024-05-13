package com.example.hungrygo.app.home.restaurant.fragment.orders.add

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.Item_Orders.Companion.Add_item_Orders
import com.example.hungrygo.databinding.FragmentAddItemDelBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class Add_item_delFragment : BottomSheetDialogFragment() {
    lateinit var dataBinding: FragmentAddItemDelBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_item_del, container, false)
        return dataBinding.root
    }


    val userid = Firebase.auth.currentUser?.uid
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.done.setOnClickListener {
            if (checkerror()) {
                val location = dataBinding.location.editText?.text.toString()
                val price = dataBinding.price.editText?.text.toString()
                val itemOrders = Item_Orders(
                    location = location,
                    price = price
                )
                Add_item_Orders(userid!!, itemOrders, OnSuccessListener {}, OnFailureListener {})
                dismiss()
            }
        }

    }

    fun checkerror(): Boolean {
        var isvalid = true
        if (dataBinding.location.editText?.text.isNullOrBlank()) {
            dataBinding.location.error = "please enter location"
            isvalid = false
            handler(dataBinding.location)
        }
        if (dataBinding.price.editText?.text.isNullOrBlank()) {
            dataBinding.price.error = "please enter price"
            isvalid = false
            handler(dataBinding.price)
        }
        return isvalid
    }

    fun handler(error: TextInputLayout) {
        Handler(Looper.getMainLooper()).postDelayed({ error.error = null }, 2000)
    }

}