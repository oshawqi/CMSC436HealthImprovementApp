package com.example.healthimprovementapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout

class ExerciseListAdaptor(context : Context, resource : Int, workout : Workout) : ArrayAdapter<String>(context, resource) {

    private val workout : Workout = workout
    private var mLayoutInflater : LayoutInflater = LayoutInflater.from(context)
    private lateinit var mExerciseTitleView : TextView
    private lateinit var mSetsView : TextView
    private lateinit var mWeightView : TextView
    private lateinit var mRepsView : TextView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val listView : View
        if (convertView == null) {
            listView = mLayoutInflater.inflate(R.layout.exercise_list, parent, true)
            mExerciseTitleView = listView.findViewById(R.id.workoutName)
            mSetsView = listView.findViewById(R.id.sets)
            mWeightView = listView.findViewById(R.id.weight)
            mRepsView = listView.findViewById(R.id.reps)

            mExerciseTitleView.text = workout.workoutExercises[position].exerciseName
        } else {
            listView = convertView
        }


        return listView
    }
}