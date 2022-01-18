package com.example.consultantalif.utils.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.example.consultantalif.databinding.ConfirmAlertBinding

object AlifAlert {

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
}