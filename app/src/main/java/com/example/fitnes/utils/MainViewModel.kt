package com.example.fitnes.utils

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnes.adapters.ExerciseModel

class MainViewModel: ViewModel() {
    val mutableListExercise = MutableLiveData<ArrayList<ExerciseModel>>()
    var pref: SharedPreferences? = null
    var currentDay = 0

    fun save(key: String, value: Int){
        pref?.edit()?.putInt(key,value)?.apply()
    }

    fun getSave(): Int {
        return pref?.getInt(currentDay.toString(), 0) ?: 0
    }
}