package com.kichai.kichai.tools

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

//    TODO: changed delayMillis to 100 for experiment. set back to 300.
    fun startLoadingDialog(runnable: Runnable = mRunnable, delayMillis: Long? = 100){
        layoutInflater = LayoutInflater.from(context)
        dialog = AlertDialog.Builder(context).create()
        dialog.setView(layoutInflater.inflate(R.layout.loading_dialog, null))
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.show()

        val handler = Handler()
        if (delayMillis != null) {
            handler.postDelayed(mRunnable, delayMillis)
        }
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}
