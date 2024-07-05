package com.example.hungrygo.app.home.manger

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.hungrygo.R
import com.example.hungrygo.app.model.appUser_delivery
import com.example.hungrygo.databinding.ProfileDialogfragmentBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class DialogFragment_profile(val context_manger: Context, val item: appUser_delivery) : DialogFragment() {
    lateinit var dataBinding: ProfileDialogfragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.profile_dialogfragment, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context_manger).load(item.image).into(dataBinding.imageProfile)
        dataBinding.name.text = item.name
        dataBinding.number.text = item.mobile_number
        if (item.job.equals("freelance") || item.job.equals("Freelance")) {
            dataBinding.job.text = getString(R.string.freelance)
        } else {
            dataBinding.job.text = item.job
        }
        dataBinding.number.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${dataBinding.number.text}")
            }
            startActivity(intent)
        }
        Switch()
        Status()
        open_close_shift()

    }

    fun Switch() {
        dataBinding.control.isChecked = item.control!!

        if (dataBinding.control.isChecked) {
            dataBinding.control.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock_close, 0, 0, 0
            )
        } else {
            dataBinding.control.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock_open, 0, 0, 0
            )
        }

        dataBinding.control.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val hash = hashMapOf("control" to true)
                Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                    .document(item.id!!).update(hash as Map<String, Any>)
                dataBinding.control.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.lock_close, 0, 0, 0
                )

            } else {
                val hash = hashMapOf("control" to false)
                Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                    .document(item.id!!).update(hash as Map<String, Any>)
                dataBinding.control.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.lock_open, 0, 0, 0
                )

            }
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    fun Status() {
        Firebase.firestore.collection(appUser_delivery.Collection_name_delivery).document(item.id!!)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e(ContentValues.TAG, "Listen failed.", error)
                    return@addSnapshotListener
                }
                if (value != null) {
                    val items = value.toObject(appUser_delivery::class.java)
                    if (items!!.status.equals("online")) {
                        Glide.with(context_manger)
                            .load(context_manger.getDrawable(R.drawable.circle_online))
                            .into(dataBinding.status)
                    } else {
                        Glide.with(context_manger)
                            .load(context_manger.getDrawable(R.drawable.circle_offline))
                            .into(dataBinding.status)
                    }
                } else {
                    Log.d(ContentValues.TAG, "Current data: null")
                }
            }
    }

    fun open_close_shift(){
        dataBinding.openShift.setOnClickListener {
            dataBinding.openShift.isEnabled=false
            dataBinding.closeShift.isEnabled=false
            val hash= hashMapOf("status" to "online")
            Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                .document(item.id!!).update(hash as Map<String, Any>).addOnSuccessListener {
                    dataBinding.openShift.isEnabled=true
                    dataBinding.closeShift.isEnabled=true
                }
        }
        dataBinding.closeShift.setOnClickListener {
            dataBinding.openShift.isEnabled=false
            dataBinding.closeShift.isEnabled=false
            val hash= hashMapOf("status" to "offline")
            Firebase.firestore.collection(appUser_delivery.Collection_name_delivery)
                .document(item.id!!).update(hash as Map<String, Any>).addOnSuccessListener {
                    dataBinding.openShift.isEnabled=true
                    dataBinding.closeShift.isEnabled=true
                }
        }
    }

}