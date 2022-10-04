package com.example.fitnes.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnes.R
import com.example.fitnes.adapters.DayModel
import com.example.fitnes.adapters.DaysAdapter
import com.example.fitnes.adapters.ExerciseModel
import com.example.fitnes.databinding.FragmentDaysBinding
import com.example.fitnes.utils.DialogManeger
import com.example.fitnes.utils.FragmentManager
import com.example.fitnes.utils.MainViewModel

class DaysFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var adapter: DaysAdapter
    private lateinit var binding: FragmentDaysBinding
    private var ab: ActionBar? = null
    private val model: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0
        binding.reBt.setOnClickListener{
            DialogManeger.showDialog(activity as AppCompatActivity,
                R.string.reset_days_massage,
            object: DialogManeger.Listener{
                override fun onClick() {
                    model.pref?.edit()?.clear()?.apply()
                    adapter.submitList(fillDaysArray())
                }
            })
        }
        initRcView()
    }


    private fun initRcView() =  with(binding){
        adapter = DaysAdapter(this@DaysFragment)
        ab = (activity as AppCompatActivity).supportActionBar
        ab?.title = getString(R.string.day_exercise)
        rcViewDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcViewDays.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private fun fillDaysArray(): ArrayList<DayModel>{
        var daysDoneCounter = 0
        val tArray = ArrayList<DayModel>()
        resources.getStringArray(R.array.day_exercise).forEach {
            model.currentDay++
            val exCounter = it.split(",").size
            tArray.add(DayModel(it, model.getSave() == exCounter,0))
        }
        binding.pB.max = tArray.size
        tArray.forEach {
            if(it.isDone) daysDoneCounter++
        }
        updateRestDaysUI(tArray.size - daysDoneCounter, tArray.size)
        return tArray
    }

    private fun updateRestDaysUI(restDays: Int, days: Int) = with(binding){
        val rDays = "${getString(R.string.rest)}: " + "$restDays" + " ${getString(R.string.days)}"
        tvRestDays.text = rDays
        pB.progress = days - restDays
    }

    private fun fillExerciseModel(day: DayModel){
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach() {
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(ExerciseModel(exerciseArray[0],exerciseArray[1], exerciseArray[2], false))
        }
        model.mutableListExercise.value = tempList
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        if(!day.isDone) {
            fillExerciseModel(day)
            model.currentDay = day.dayNumber
            FragmentManager.setFragment(
                ExerciseListFragment.newInstance(),
                activity as AppCompatActivity)
        } else{
            DialogManeger.showDialog(activity as AppCompatActivity,
                R.string.reset_one_day_massage,
                object: DialogManeger.Listener{
                    override fun onClick() {
                        model.save(day.dayNumber.toString(), 0)
                        fillExerciseModel(day)
                        model.currentDay = day.dayNumber
                        FragmentManager.setFragment(
                            ExerciseListFragment.newInstance(),
                            activity as AppCompatActivity)
                    }
                })
        }
    }
}