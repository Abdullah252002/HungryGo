package com.example.hungrygo.app.home.customer.fragment.restaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hungrygo.R
import com.example.hungrygo.databinding.FragmentMenuBinding
import com.example.hungrygo.databinding.FragmentRestaurantBinding

class Restaurant_fragment:Fragment() {
    lateinit var  dataBinding: FragmentRestaurantBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant,container,false)
        return dataBinding.root
    }
}