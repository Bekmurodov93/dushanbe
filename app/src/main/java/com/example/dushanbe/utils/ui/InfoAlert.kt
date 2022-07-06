package com.example.dushanbe.utils.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.example.dushanbe.R
import com.example.dushanbe.databinding.ConfirmAlertBinding

object InfoAlert {

    var dialog: AlertDialog.Builder? = null
    var alertDialog: AlertDialog? = null
    private var dialogProcess: Dialog? = null

    //redesign
    fun showConfirmAlert(context: Context, title: String, message: String, positiveBtnText: String, negativeBtnText: String, denyFun: () -> Unit = { Unit }, confirmFun: () -> Unit) {
        if (alertDialog != null && alertDialog!!.isShowing) return
        val dialogBuilder = AlertDialog.Builder(context)
        val binding = ConfirmAlertBinding.inflate(LayoutInflater.from(context))
        if (title.isNotEmpty()){
            binding.title.visibility = View.VISIBLE
            binding.title.text = title
        }
        if (message.isNotEmpty()){
            binding.message.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.message.movementMethod= LinkMovementMethod.getInstance()
            binding.message.visibility = View.VISIBLE
        }
        binding.confirm.text = positiveBtnText
        binding.cancel.text = negativeBtnText

        binding.confirm.setOnClickListener {
            alertDialog?.dismiss()
            confirmFun.invoke()
        }
        binding.cancel.setOnClickListener {
            denyFun.invoke()
            alertDialog?.dismiss()
        }
        dialogBuilder.setView(binding.root)
        alertDialog = dialogBuilder.show()
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.setOnDismissListener { alertDialog = null }
    }
    fun showWarningAlert(context: Context, title: String, message: String, confirmFun: () -> Unit) {
        if (alertDialog != null && alertDialog!!.isShowing) return
        val dialogBuilder = AlertDialog.Builder(context)
        val binding = ConfirmAlertBinding.inflate(LayoutInflater.from(context))
        if (title.isNotEmpty()){
            binding.title.visibility = View.VISIBLE
            binding.title.text = title
        }
        if (message.isNotEmpty()){
            binding.message.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.message.movementMethod= LinkMovementMethod.getInstance()
            binding.message.visibility = View.VISIBLE
        }
        binding.confirm.text = context.getString(R.string.ok)
        binding.cancel.invisible()

        binding.confirm.setOnClickListener {
            alertDialog?.dismiss()
            confirmFun.invoke()
        }

        dialogBuilder.setView(binding.root)
        alertDialog = dialogBuilder.show()
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.setOnDismissListener { alertDialog = null }
    }
    fun showProgressDialog(context: Context){
        if (dialogProcess != null && dialogProcess!!.isShowing) {
            return
        }
        dialogProcess =  Dialog(context)
        dialogProcess!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogProcess!!.setContentView(R.layout.custom_process_dialog)
        dialogProcess!!.setCancelable(false)
        dialogProcess!!.show()

    }
    fun hideProgressDialog(){
        if (dialogProcess != null && dialogProcess!!.isShowing) {
            dialogProcess!!.dismiss();
        }
    }
}