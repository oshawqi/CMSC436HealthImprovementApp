package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.healthimprovementapp.R

class ExerciseList(private val context: Activity, private var exercises: List<Exercise>) : ArrayAdapter<Exercise>(context, R.layout.workout_list, exercises) {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflator = context.layoutInflater
        val listViewItem = inflator.inflate(R.layout.workout_list, null, true)

        //TODO: Update the XML file to account for multiple exercises
        val textViewName = listViewItem.findViewById<View>(R.id.exerciseName) as TextView
        //val textViewExercise = listViewItem.findViewById<View>(R.id.textViewExercise) as TextView

        val exercise = exercises[position]
        textViewName.text = exercise.exerciseName
        //textViewExercise.text = exercise.workoutExercises[0]

        return listViewItem
    }
}