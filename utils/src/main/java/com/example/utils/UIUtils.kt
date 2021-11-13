package com.example.utils

import android.app.AlertDialog
import android.content.Context

fun getStubAlertDialog(context: Context): AlertDialog {
    return getAlertDialog(context, null, null)
}

fun getAlertDialog(context: Context, title: String?, message: String?): AlertDialog {
    val builder = AlertDialog.Builder(context)
    if (!title.isNullOrBlank()) builder.setTitle(title)
        else builder.setTitle(context.getString(R.string.dialog_title_stub))
    if (!message.isNullOrBlank()) builder.setMessage(message)
    builder.setCancelable(true)
    builder.setPositiveButton(R.string.dialog_button_cancel) { dialog, _ -> dialog.dismiss() }
    return builder.create()
}