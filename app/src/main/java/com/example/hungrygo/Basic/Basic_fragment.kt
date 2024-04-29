package com.example.hungrygo.Basic

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.chat.Basic.Basic_Viewmodel
import com.google.android.material.appbar.MaterialToolbar

abstract class Basic_fragment<DB : ViewDataBinding,VM : Basic_Viewmodel<*>>:Fragment() {
    lateinit var viewModel: VM
    lateinit var dataBinding: DB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return dataBinding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewmodel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    abstract fun initViewmodel(): VM
    abstract fun getLayoutId(): Int
    private fun observeLiveData() {
        viewModel.messageLiveData.observe(viewLifecycleOwner, { message ->
            showDialog(message, "OK")
        })

        viewModel.showDialog.observe(viewLifecycleOwner, { shouldShow ->
            if (shouldShow) {
                showProgress()
            } else {
                hideProgress()
            }
        })
    }

    private var alertDialog: AlertDialog? = null

    fun showDialog(
        message: String? = null,
        posActionName: String? = null,
        posAction: DialogInterface.OnClickListener? = null,
        negActionName: String? = null,
        negAction: DialogInterface.OnClickListener? = null,
        cancel: Boolean = true
    ) {
        val defaultAction = DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        }

        val builder = AlertDialog.Builder(requireContext()).setMessage(message)
        if (posActionName != null) {
            builder.setPositiveButton(posActionName, posAction ?: defaultAction)
        }
        if (negActionName != null) {
            builder.setNegativeButton(negActionName, negAction ?: defaultAction)
        }
        builder.setCancelable(cancel)

        alertDialog = builder.show()
    }

    fun hideDialog() {
        alertDialog?.dismiss()
        alertDialog = null
    }

    private var progressDialog: ProgressDialog? = null

    fun showProgress() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.setMessage("Loading...")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    fun hideProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideDialog()
        hideProgress()
    }

    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.dismiss()
        progressDialog?.dismiss()
    }

}