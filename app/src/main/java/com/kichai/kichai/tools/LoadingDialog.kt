package com.kichai.kichai.tools

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.LayoutInflater
import com.kichai.kichai.R

class LoadingDialog(val context: Context) {
    private lateinit var dialog: AlertDialog
    lateinit var layoutInflater: LayoutInflater
    var mRunnable = Runnable { dismissDialog() }

    fun startLoadingDialog(runnable: Runnable = mRunnable, delayMillis: Long = 300){
        layoutInflater = LayoutInflater.from(context)
        dialog = AlertDialog.Builder(context).create()
        dialog.setView(layoutInflater.inflate(R.layout.loading_dialog, null))
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        val handler = Handler()
        handler.postDelayed(mRunnable, delayMillis)
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}
