package com.example.hungrygo.app.home.manger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_delivery

class DialogFragment_profile(val item: appUser_delivery) :DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_dialogfragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profile_image = view.findViewById<ImageView>(R.id.image_profile)
        Glide.with(requireContext()).load(item.image).into(profile_image)
    }
}