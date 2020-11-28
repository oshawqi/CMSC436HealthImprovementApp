package com.example.healthimprovementapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout
import kotlinx.android.synthetic.main.list_item.view.*

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
            val numSets = findViewById<View>(R.id.numSets).text.toString().toInt()
            val numReps = findViewById<View>(R.id.numReps).text.toString().toInt()
            val numWeight = findViewById<View>(R.id.numWeight).text.toString().toInt()

            if (exerciseName != null && exerciseName != "") {

                addExercise(exerciseName, numSets, numReps, numWeight)
            } else {
                Toast.makeText(this, "Please enter an exercise name", Toast.LENGTH_LONG)
            }
        }

        mSubmitWorkoutButton.setOnClickListener {
            if (mExerciseListAdapter.count == 0) {
                //TODO -> add an alert dialog to ask if they want to submit a workout with no exercises
            } else {
                val returnIntent = Intent(this, WorkoutActivity::class.java)
                returnIntent.putExtra(WORKOUT_NAME, workout)
                setResult(REQUEST_CODE, returnIntent)
                finish()
            }
        }
    }

    private fun addExercise(exerciseName : String, numSets: Int, numReps: Int, numWeight: Int) {
        val newExercise = Exercise(exerciseName, numSets, numReps, numWeight)
        workout.exerciseList.add(newExercise) //Adds the new exercise to this workout's exercise list
        mExerciseListAdapter.add(newExercise) //Adds this new exercise to the visible exercise list on the UI
    }

    companion object {
        val TAG = "Mine-AddWorkoutActivity:"
        const val WORKOUT_NAME = "WORKOUT_NAME"
        const val WORKOUT_ID = "WORKOUT_ID"
        const val WORKOUT_EXERCISES = "WORKOUT_EXERCISES"
        val USER_ID = "USER_ID"
        val WORKOUT_TYPE = "WORKOUT_TYPE"
        val BULK_UP = "BULK_UP"
        val WEIGHT_LOSS = "WEIGHT_LOSS"
        val ENDURANCE = "ENDURANCE"
        val FLEXIBILITY = "FLEXIBILITY"
        const val REQUEST_CODE = 2
    }
}