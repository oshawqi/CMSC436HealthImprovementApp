package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.healthimprovementapp.R

class WorkoutListAdaptor(context: Activity, workouts: List<Workout>) : ArrayAdapter<Workout>(context, R.layout.workout_list, workouts) {
    private var context : Activity = context
    private var workouts : ArrayList<Workout> = workouts as ArrayList<Workout>
    private var mLayoutInflater : LayoutInflater = LayoutInflater.from(context)
    private lateinit var mWorkoutNameTextView : TextView
    private lateinit var mExercisesTextView : TextView

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listView : View

        if (convertView == null) {
            listView = mLayoutInflater.inflate(R.layout.workout_list, parent, true)

            mWorkoutNameTextView = listView.findViewById(R.id.workoutListWorkoutName)
            mExercisesTextView = listView.findViewById(R.id.workoutListExerciseNames)

            //TODO: Update the XML file to account for multiple exercises
            val workout = workouts[position]

            mWorkoutNameTextView.text = workout.workoutName
            mExercisesTextView.text = getExercisesString(workout.workoutExercises)

        } else {
            listView = convertView
        }
        return listView
    }

    private fun numListElements() : Int {
        return workouts.size
    }

    fun addWorkout(workout : Workout, listView : ViewGroup) {
        workouts.add(workout)
        val pos = numListElements()
        listView.addView(this.getView(pos, null, listView))
    }
    //Takes a list of exercises and returns them in a string list with the format:
    //  exercise1, exercise2, ..., exerciseN
    private fun getExercisesString(exercises : List<Exercise>) : String {
        var exerciseString : StringBuffer = StringBuffer()
        val numExercises = exercises.size

        exerciseString.append(exercises[0].exerciseName)

        for (i in 1 until numExercises) {
            val name = exercises[i].exerciseName
            exerciseString.append(", $name")
        }

        return exerciseString.toString()

    }
}