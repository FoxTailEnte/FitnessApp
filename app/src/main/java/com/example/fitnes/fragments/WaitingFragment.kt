package com.example.fitnes.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fitnes.R
import com.example.fitnes.databinding.WaitingFragmentBinding
import com.example.fitnes.utils.FragmentManager
import com.example.fitnes.utils.TimeUtils

const val COUNT_DOWN_TIMER = 11000L

class WaitingFragment : Fragment() {
private lateinit var binding: WaitingFragmentBinding
private lateinit var timer: CountDownTimer
private var ab: ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WaitingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.get_ready)
        binding.pb.max = COUNT_DOWN_TIMER.toInt()
        startTimer()
    }

    private fun startTimer() =  with(binding){
        timer = object: CountDownTimer(COUNT_DOWN_TIMER, 1){
            override fun onTick(restTime: Long) {
                tvTime.text = TimeUtils.getTime(restTime)
                pb.progress = restTime.toInt()
            }

            override fun onFinish() {
                FragmentManager.setFragment(ExerciseFragment.newInstance(), activity as AppCompatActivity)

            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer.cancel()
    }
    companion object {
        @JvmStatic
        fun newInstance() = WaitingFragment()
    }
}