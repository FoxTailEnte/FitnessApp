package com.example.fitnes.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnes.R

object FragmentManager {
    var currentFragment: Fragment? = null
    fun setFragment(newFragment: Fragment, act: AppCompatActivity){

        val transaction = act.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        transaction.replace(R.id.placeHolder, newFragment)
        transaction.commit()
        currentFragment = newFragment
    }
}