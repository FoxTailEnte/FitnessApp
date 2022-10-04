package com.example.fitnes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.fitnes.databinding.ActivityMainBinding
import com.example.fitnes.fragments.DaysFragment
import com.example.fitnes.utils.FragmentManager
import com.example.fitnes.utils.MainViewModel

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model.pref = getSharedPreferences("main", MODE_PRIVATE)
        FragmentManager.setFragment(DaysFragment.newInstance(), this)
    }

    override fun onBackPressed() {
        if (FragmentManager.currentFragment is DaysFragment)
        super.onBackPressed()
        else FragmentManager.setFragment(DaysFragment.newInstance(), this)
    }
}