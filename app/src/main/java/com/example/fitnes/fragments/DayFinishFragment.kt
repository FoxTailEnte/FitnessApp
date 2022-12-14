package com.example.fitnes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnes.R
import com.example.fitnes.databinding.DayFinishBinding
import com.example.fitnes.utils.FragmentManager
import pl.droidsonroids.gif.GifDrawable

class DayFinishFragment : Fragment() {
    private var ab: ActionBar? = null
    private lateinit var binding: DayFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DayFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.done)
        binding.imageView2.setImageDrawable(GifDrawable((activity as AppCompatActivity).assets, "congrats-congratulations.gif"))
        binding.button.setOnClickListener {
            FragmentManager.setFragment(DaysFragment.newInstance(),activity as AppCompatActivity)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = DayFinishFragment()
    }
}