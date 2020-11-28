package com.example.healthimprovementapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout
import kotlinx.android.synthetic.*
import kotlin.random.Random

class AddWorkoutActivity : Activity() {
    private var workoutName : String? = null
    private var workoutID : String? = null
    private lateinit var workout : Workout

    private lateinit var mNameTextView : TextView
    private lateinit var mExerciseNameEditText : EditText
    private lateinit var mAddExerciseButton : Button
    private lateinit var mSubmitWorkoutButton : Button
    private lateinit var mExerciseListView : ListView

    private lateinit var mExerciseListAdapter : ExerciseListAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_workout)

        if (intent != null) {
            workoutName = intent.getStringExtra(WORKOUT_NAME)
            workoutID = intent.getStringExtra(WORKOUT_ID)
            workout = Workout(workoutID!!, workoutName!!, ArrayList<Exercise>(0))
        } else {
            finish()
        }

        //Get references to views and set the workout name
        mNameTextView = findViewById(R.id.addWorkoutName)
        mNameTextView.text = workoutName
        mExerciseNameEditText = findViewById(R.id.addWorkoutExerciseName)
        mAddExerciseButton = findViewById(R.id.addWorkoutAddExerciseButton)
        mSubmitWorkoutButton = findViewById(R.id.addWorkoutSubmitButton)

        //set up the list view and adapter
        mExerciseListView = findViewById(R.id.addWorkoutExerciseList)
        mExerciseListAdapter = ExerciseListAdaptor(this, workoutName!!)
        mExerciseListView.adapter = mExerciseListAdapter

        //TODO -> add a button or listener to remove exercises from the list
        //onclick listener for adding exercises to the list
        mAddExerciseButton.setOnClickListener {
            val exerciseName = mExerciseNameEditText.text.toString()

            if (exerciseName != null && exerciseName != "") {
                addExercise(exerciseName)
            } else {
                Toast.makeText(this, "Please enter an exercise name", Toast.LENGTH_LONG)
            }
        }

        mSubmitWorkoutButton.setOnClickListener {
            if (mExerciseListAdapter.count == 0) {
                //TODO -> add an alert dialog to ask if they want to submit a workout with no exercises
            } else {
                //TODO -> package the exercises as an intent and set it as the result for the activity
            }
        }
    }

    private fun addExercise(exerciseName : String) {
        val newExercise = Exercise("exercise", exerciseName)
        mExerciseListAdapter.add(newExercise)
    }

    companion object {
        val TAG = "Mine-AddWorkoutActivity:"
        const val WORKOUT_NAME = "com.example.tesla.myhomelibrary.authorname"
        const val WORKOUT_ID = "com.example.tesla.myhomelibrary.authorid"
    }
}