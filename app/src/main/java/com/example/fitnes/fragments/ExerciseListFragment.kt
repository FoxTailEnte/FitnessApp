package com.example.fitnes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnes.R
import com.example.fitnes.adapters.ExerciseAdapter
import com.example.fitnes.databinding.ExerciseListFragmentBinding
import com.example.fitnes.utils.FragmentManager
import com.example.fitnes.utils.MainViewModel

class ExerciseListFragment : Fragment() {
    private lateinit var binding: ExerciseListFragmentBinding
    private val model: MainViewModel by activityViewModels()
    private var ab: ActionBar? = null
    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.exercises)
        init()
        model.mutableListExercise.observe(viewLifecycleOwner){
            for(i in 0 until model.getSave()){
                it[i] = it[i].copy(isDone = true)
            }
            adapter.submitList(it)
        }

    }

    private fun init() =  with(binding){
        adapter = ExerciseAdapter()
        rcEx.layoutManager = LinearLayoutManager(activity)
        rcEx.adapter = adapter
        bStar.setOnClickListener{
            FragmentManager.setFragment(WaitingFragment.newInstance(), activity as AppCompatActivity)
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() = ExerciseListFragment()
    }
}