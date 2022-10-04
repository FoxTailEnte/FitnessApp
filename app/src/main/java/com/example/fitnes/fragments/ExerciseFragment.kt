package com.example.fitnes.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.fitnes.R
import com.example.fitnes.adapters.ExerciseModel
import com.example.fitnes.databinding.ExerciseBinding
import com.example.fitnes.utils.FragmentManager
import com.example.fitnes.utils.MainViewModel
import com.example.fitnes.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable

class ExerciseFragment : Fragment() {
    private var timer: CountDownTimer? = null
    private lateinit var binding: ExerciseBinding
    private var ab: ActionBar? = null
    private var exerciseCounter = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private val model: MainViewModel by activityViewModels()
    private var currentDay = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = model.currentDay
        exerciseCounter = model.getSave()
        ab = (activity as AppCompatActivity).supportActionBar
        model.mutableListExercise.observe(viewLifecycleOwner){
            exerciseList = it
            nextExercise()
        }
        binding.btNext.setOnClickListener {
            nextExercise()
        }
    }

/* #1 */
    private fun nextExercise(){
        if(exerciseCounter < exerciseList?.size!!){
            val ex = exerciseList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            setExerciseType(ex)
            showNextExercise()
        } else {
            exerciseCounter++
            FragmentManager.setFragment(DayFinishFragment.newInstance(), activity as AppCompatActivity)

        }
    }

    /* #5 */
    private fun showNextExercise() = with(binding){
        if(exerciseCounter < exerciseList?.size!!){
            val ex = exerciseList?.get(exerciseCounter) ?: return
            imNext.setImageDrawable(GifDrawable(root.context.assets, ex.image))
            tvNext.text = ex.name
            setTimeType(ex)
        } else {
            imNext.setImageDrawable(GifDrawable(root.context.assets, "congrats-congratulations.gif"))
            tvNext.text = getString(R.string.complete)
        }
    }

    /* #6 */
    private fun setTimeType(exercise: ExerciseModel){
        if(exercise.time.startsWith("x")){
            binding.tvNext.text = exercise.time
        }else{
            val name = exercise.name + ": ${TimeUtils.getTime(exercise.time.toLong()*1000)}"
            binding.tvNext.text = name
        }
    }

    /* #3 */
    private fun setExerciseType(exercise: ExerciseModel){
        if(exercise.time.startsWith("x")){
            binding.progressBar.progress = 0
            timer?.cancel()
            binding.tvTime.text = exercise.time
        }else{
            startTimer(exercise)
        }
    }

    /* #4 */
    private fun startTimer(exercise: ExerciseModel) =  with(binding){
        progressBar.max = exercise.time.toInt() * 1000
        timer?.cancel()
        timer = object: CountDownTimer(exercise.time.toLong()*1000, 1){
            override fun onTick(restTime: Long) {
                tvTime.text = TimeUtils.getTime(restTime)
                progressBar.progress = restTime.toInt()
            }

            override fun onFinish() {
                nextExercise()

            }

        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        model.save(currentDay.toString(), exerciseCounter - 1)
        timer?.cancel()
    }

    /* #2 */
    private fun showExercise(exercise: ExerciseModel)=with(binding){
        imMain.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        tvName.text = exercise.name
        val titel = "$exerciseCounter / ${exerciseList?.size}"
        ab?.title = titel
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}