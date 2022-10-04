package com.example.fitnes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnes.R
import com.example.fitnes.databinding.ExerciseListItemBinding
import pl.droidsonroids.gif.GifDrawable


class ExerciseAdapter: ListAdapter<ExerciseModel, ExerciseAdapter.ExerciseHolder>(ExerciseAdapter.MyComparator()) {

 class ExerciseHolder(view: View): RecyclerView.ViewHolder(view){
     private val binding = ExerciseListItemBinding.bind(view)

        fun setData(day: ExerciseModel) = with(binding){
            textName.text = day.name
            textInt.text = day.time
            checkBox.isChecked = day.isDone
            imV.setImageDrawable(GifDrawable(root.resources.assets, day.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {

        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.exercise_list_item, parent, false)
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }
    class MyComparator: DiffUtil.ItemCallback<ExerciseModel>() {
        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }
    }
}