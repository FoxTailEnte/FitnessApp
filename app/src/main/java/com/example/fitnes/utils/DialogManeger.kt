package com.example.fitnes.utils

import android.app.AlertDialog
import android.content.Context
import com.example.fitnes.R

object DialogManeger {
    fun showDialog(context: Context, mId: Int, listener: Listener){
        var builder = AlertDialog.Builder(context)
        var dialog: AlertDialog? = null
        builder.setTitle(R.string.alert)
        builder.setMessage(mId)
        builder.setNegativeButton(R.string.back){_,_ ->
            dialog?.dismiss()
        }
        builder.setPositiveButton(R.string.reset){_,_ ->
            listener.onClick()
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }
    interface Listener{
        fun onClick()

    }
}