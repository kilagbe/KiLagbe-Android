package com.kichai.kichai.tools

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.kichai.kichai.R

class LoadingDialog(val context: Context) {
    private lateinit var dialog: AlertDialog
    lateinit var layoutInflater: LayoutInflater

    fun startLoadingDialog(){
        layoutInflater = LayoutInflater.from(context)
        dialog = AlertDialog.Builder(context).create()
        dialog.setView(layoutInflater.inflate(R.layout.loading_dialog, null))
        dialog.setCancelable(false)
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}