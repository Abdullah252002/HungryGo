package com.example.hungrygo.app.home.restaurant.fragment.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hungrygo.Basic.Basic_fragment
import com.example.hungrygo.R
import com.example.hungrygo.databinding.FragmentOrdersBinding

class Orders_fragment : Basic_fragment<FragmentOrdersBinding,Orders_fragment_viewmodel>(),Navigator {
    override fun initViewmodel(): Orders_fragment_viewmodel {
        return ViewModelProvider(this).get(Orders_fragment_viewmodel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_orders
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}