package com.example.healthimprovementapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.list_item.view.*

class AddWorkoutActivity : AppCompatActivity() {
    private var workoutName : String? = null
    private  var workoutExercises = ArrayList<Exercise>()
    private lateinit var databaseWorkouts: DatabaseReference
    private lateinit var workoutType : String
    private lateinit var uid: String


    private lateinit var mNameTextView : TextView
    private lateinit var mExerciseNameEditText : EditText
    private lateinit var mSetsEditText : EditText
    private lateinit var mRepsEditText : EditText
    private lateinit var mWeightEditText : EditText
    private lateinit var mAddExerciseButton : Button
    private lateinit var mSubmitWorkoutButton : Button
    private lateinit var mCancelButton : Button
    private lateinit var mExerciseListView : ListView

    private lateinit var mExerciseListAdapter : ExerciseListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_workout)

        if (intent != null) {
            workoutName = intent.getStringExtra(WORKOUT_NAME)
            workoutType = intent.getStringExtra(WORKOUT_TYPE)
            uid = intent.getStringExtra(USER_ID)

            //Access the workout's node in the database
            databaseWorkouts = FirebaseDatabase.getInstance().getReference("users").child(uid).child(workoutType)                       //TEST

        } else {
            finish()
        }

        //Get references to views and set the workout name
        mNameTextView = findViewById(R.id.addWorkoutName)
        mNameTextView.text = workoutName
        mExerciseNameEditText = findViewById(R.id.addWorkoutExerciseName)
        mSetsEditText = findViewById(R.id.numSets)
        mWeightEditText = findViewById(R.id.numWeight)
        mRepsEditText = findViewById(R.id.numReps)
        mAddExerciseButton = findViewById(R.id.addWorkoutAddExerciseButton)
        mSubmitWorkoutButton = findViewById(R.id.addWorkoutSubmitButton)
        mCancelButton = findViewById(R.id.addWorkoutCancelButton)

        //set up the list view and adapter
        mExerciseListView = findViewById(R.id.addWorkoutExerciseList)
        mExerciseListAdapter = ExerciseListAdapter(this)
        mExerciseListView.adapter = mExerciseListAdapter

    }

    override fun onStart() {
        super.onStart()

        mExerciseListView.setOnItemClickListener { adapterView, view, i, l ->
            val exercise = mExerciseListAdapter.getItem(i) as Exercise
            mExerciseListAdapter.removeAt(i)
            mExerciseNameEditText.setText(exercise.exerciseName)
            mSetsEditText.setText(exercise.numSets.toString())
            mRepsEditText.setText(exercise.numReps.toString())
            mWeightEditText.setText(exercise.weight.toString())
        }

        mExerciseListView.setOnItemLongClickListener { adapterView, view, i, l ->
            var mDialog = DeleteDialogFragment.newInstance(i as Object, EXERCISE)
            mDialog.show(supportFragmentManager, "DeleteDialog")
            true
        }
        mAddExerciseButton.setOnClickListener {
            addExercise()
        }

        mSubmitWorkoutButton.setOnClickListener {
            submitWorkout()
        }

        mCancelButton.setOnClickListener {
            cancelAddWorkout()
        }
    }

    internal fun deleteExercise(pos : Int) {
        mExerciseListAdapter.removeAt(pos)
    }

    private fun addExercise() {
        val exerciseName = mExerciseNameEditText.text.toString()
        val numSets = mSetsEditText.text.toString().toInt()
        val numReps = mRepsEditText.text.toString().toInt()
        val numWeight = mWeightEditText.text.toString().toInt()

        if (exerciseName != null && exerciseName != "") {
            mExerciseNameEditText.setText("")
            findViewById<EditText>(R.id.numSets).setText("")
            findViewById<EditText>(R.id.numReps).setText("")
            findViewById<EditText>(R.id.numWeight).setText("")

            val newExercise = Exercise(exerciseName, numSets, numReps, numWeight)
            workoutExercises.add(newExercise) //Adds the new exercise to this workout's exercise list
            mExerciseListAdapter.add(newExercise) //Adds this new exercise to the visible exercise list on the UI

            Log.i(TAG, "Exercise added (Name: $exerciseName, Sets: $numSets, Reps: $numReps, Weight: $numWeight")
        } else {
        Toast.makeText(this, "Please enter an exercise name", Toast.LENGTH_LONG)
        }
    }

    private fun submitWorkout() {
        if (mExerciseListAdapter.count == 0) {
            //TODO -> add an alert dialog to ask if they want to submit a workout -with no exercises
        } else {

            Toast.makeText(this,"Submitting",Toast.LENGTH_SHORT)

            //Add to the database
            val id = databaseWorkouts.push().key
            val workout = Workout(id!!, workoutName, workoutExercises)
            databaseWorkouts.child(id!!).setValue(workout)

            //Generate the return intent to go back to main activity
            val returnIntent = Intent().putExtra(WORKOUT_NAME, workout)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }

    private fun cancelAddWorkout() {
        setResult(RESULT_CANCELED, null)
        finish()
    }

    companion object {
        val TAG = "Mine-AddWorkoutActivity:"
        const val WORKOUT_NAME = "WORKOUT_NAME"
        const val EXERCISE = "EXERCISE"
        val USER_ID = "USER_ID"
        val WORKOUT_TYPE = "WORKOUT_TYPE"
        const val REQUEST_CODE = 2
    }
}