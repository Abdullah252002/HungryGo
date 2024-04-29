package com.example.hungrygo.app.home.restaurant.fragment.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hungrygo.Basic.Basic_fragment
import com.example.hungrygo.R
import com.example.hungrygo.databinding.FragmentDeliveryBinding
import com.google.android.material.appbar.MaterialToolbar


class Delivery_fragment : Basic_fragment<FragmentDeliveryBinding,Delivery_fragment_viewmodel>(),Navigator {

    override fun initViewmodel(): Delivery_fragment_viewmodel {
        return ViewModelProvider(this).get(Delivery_fragment_viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_delivery
    }

    lateinit var databinding:FragmentDeliveryBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }




}