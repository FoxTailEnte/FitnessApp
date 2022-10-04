package com.example.fitnes.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object TimeUtils {
    val formatter = SimpleDateFormat("mm:ss")
    fun getTime(time: Long): String{
        val cv = Calendar.getInstance()
        cv.timeInMillis = time
        return formatter.format(cv.time)
    }
}