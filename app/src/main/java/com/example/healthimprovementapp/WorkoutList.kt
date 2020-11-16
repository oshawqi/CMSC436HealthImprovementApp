package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.healthimprovementapp.R

class WorkoutList(private val context: Activity, private var workouts: List<Workout>) : ArrayAdapter<Workout>(context, R.layout.activity_workout_list, workouts) {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflator = context.layoutInflater
        val listViewItem = inflator.inflate(R.layout.activity_workout_list, null, true)

        //TODO: Update the XML file to account for multiple exercises
        val textViewName = listViewItem.findViewById<View>(R.id.textViewName) as TextView
        val textViewExercise = listViewItem.findViewById<View>(R.id.textViewExercise) as TextView

        val workout = workouts[position]
        textViewName.text = workout.workoutName
        textViewExercise.text = workout.workoutExercises[0].toString()                     //TODO: Make it populate more than the 1st exercise

        return listViewItem
    }
}