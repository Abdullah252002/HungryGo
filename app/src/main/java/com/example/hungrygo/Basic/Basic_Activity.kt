package com.example.chat.Basic

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.appbar.MaterialToolbar
open abstract class Basic_Activity<DB : ViewDataBinding, VM : Basic_Viewmodel<*>> :
    AppCompatActivity() {
    lateinit var dataBinding: DB
    lateinit var viewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = initViewmodel()
        getMaterialToolbar()?.let { showbackclick(it) }
        Livedata()
    }
    fun Livedata(){
        viewModel.messageLiveData.observe(this,{message->
            showDialog(message,"ok")
        })
        viewModel.showDialog.observe(this,{
            if(it){
                showprogress()
            }else{
                hideprogress()
            }
        })
    }
    abstract fun initViewmodel(): VM
    abstract fun getLayoutId(): Int
    abstract fun getMaterialToolbar():MaterialToolbar?
    fun showbackclick(toolbar: MaterialToolbar){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
    }
    var alertDialog: AlertDialog?=null
    fun showDialog(
        message: String? = null,
        posActionName: String? = null,
        posAction: DialogInterface.OnClickListener? = null,
        negActionName: String? = null,
        negAction: DialogInterface.OnClickListener? = null,
        cancel: Boolean = true
    ) {
        val defaultAction=object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        }
        val builder = AlertDialog.Builder(this).setMessage(message)
        if(posActionName!=null){
            builder.setPositiveButton(posActionName,posAction?:defaultAction)
        }
        if(negActionName!=null){
            builder.setNegativeButton(negActionName,negAction?:defaultAction)
        }
        builder.setCancelable(cancel)

        alertDialog=builder.show()
    }

    fun hideDialog(){
        alertDialog?.dismiss()
        alertDialog=null
    }

    var progressDialog:ProgressDialog?=null
    fun showprogress(){
        progressDialog=ProgressDialog(this)
        progressDialog?.setMessage("Loading...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }
    fun hideprogress(){
        progressDialog?.dismiss()
        progressDialog=null
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}