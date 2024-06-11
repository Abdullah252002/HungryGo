package com.example.hungrygo.app.home.restaurant.fragment.orders.add

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.Item_Orders.Companion.Add_item_Orders
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.databinding.FragmentAddItemDelBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlin.math.truncate

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

        Firebase.firestore.collection("Location").document("Al-Shorouk-City").get()
            .addOnSuccessListener {
                val list = it?.get("location") as ArrayList<String>
                val list2 = it.get("number") as ArrayList<String>
                val location_list = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    list
                )
                val number_list = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    list2
                )
                dataBinding.location.threshold = 1
                dataBinding.number.threshold = 1
                dataBinding.location.setAdapter(location_list)
                dataBinding.number.setAdapter(number_list)

            }

        dataBinding.done.setOnClickListener {
            if (checkerror()) {
                val location = dataBinding.location.text.toString()
                val price = dataBinding.price.editText?.text.toString()
                val number = dataBinding.number.text.toString()
                val itemOrders = Item_Orders(
                    location = location,
                    price = price,
                    number = number
                )
                Add_item_Orders(userid!!, itemOrders, OnSuccessListener {}, OnFailureListener {})
                add_suggestion(itemOrders)
                dataBinding.location.text = null
                dataBinding.number.text = null
                dismiss()

            }
        }

    }

    fun add_suggestion(itemOrders: Item_Orders) {
        Firebase.firestore.collection("Location").document("Al-Shorouk-City").update(
            "number", FieldValue.arrayUnion(itemOrders.number)
        )
        Firebase.firestore.collection("Location").document("Al-Shorouk-City").update(
            "location", FieldValue.arrayUnion(itemOrders.location)
        )

        Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
            .document(userid!!).get().addOnSuccessListener {
                var check= true
                val hashMap = it.get("rate_customer") as? HashMap<String, String> ?: hashMapOf()
                val keys=hashMap.keys.toList()
                for (i in keys){
                    if (i==itemOrders.number.toString()){
                        check=false
                    }
                }
                if (check){
                    hashMap[itemOrders.number.toString()] = "0"
                    Firebase.firestore.collection(appUser_restaurant.Collection_name_restaurant)
                        .document(userid).update("rate_customer", hashMap)
                }

            }

    }

    fun checkerror(): Boolean {
        var isvalid = true
        if (dataBinding.locationId.editText?.text.isNullOrBlank()) {
            dataBinding.location.error = "please enter price"
            isvalid = false
            handler(dataBinding.locationId)
        }
        if (dataBinding.price.editText?.text.isNullOrBlank()) {
            dataBinding.price.error = "please enter price"
            isvalid = false
            handler(dataBinding.price)
        }
        if (dataBinding.numberId.editText?.text.isNullOrBlank()) {
            dataBinding.numberId.error = "please enter number"
            isvalid = false
            handler(dataBinding.numberId)
        } else if (dataBinding.numberId.editText?.text.toString().length < 11 || dataBinding.numberId.editText?.text.toString().length > 11) {
            dataBinding.numberId.error = "please enter 11 number"
            isvalid = false
            handler(dataBinding.numberId)
        }
        return isvalid
    }

    fun handler(error: TextInputLayout) {
        Handler(Looper.getMainLooper()).postDelayed({ error.error = null }, 2000)
    }

}