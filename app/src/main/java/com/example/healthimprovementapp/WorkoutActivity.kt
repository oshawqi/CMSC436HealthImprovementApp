package com.example.healthimprovementapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.healthimprovementapp.com.example.healthimprovementapp.Exercise
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout
import com.example.healthimprovementapp.com.example.healthimprovementapp.WorkoutExercisesActivity
import com.example.healthimprovementapp.com.example.healthimprovementapp.WorkoutList
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.workout_list.*
import java.util.*

class WorkoutActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var buttonAddWorkout: Button
    private lateinit var listViewWorkouts: ListView
    private lateinit var workoutListAdapter : WorkoutListAdapter

    private lateinit var uid: String
    private lateinit var workoutType : String
    private lateinit var workouts : MutableList<Workout>

    private lateinit var mDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        //Initializes UID and Workout Type here based on intent provided
        uid = intent.getStringExtra(USER_ID) as String
        workoutType = intent.getStringExtra(WORKOUT_TYPE) as String

        //Access the workout's node in the database
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(uid).child(workoutType)

        //Set up views for adding workouts
        editTextName = findViewById<View>(R.id.customWorkoutName) as EditText
        buttonAddWorkout = findViewById<View>(R.id.addCustomWorkoutButton) as Button

        //setup list view and adapter
        listViewWorkouts = findViewById<View>(R.id.listViewWorkouts) as ListView
        workoutListAdapter = WorkoutListAdapter(this)
        listViewWorkouts.adapter = workoutListAdapter

        workouts = ArrayList<Workout>()

        //TODO -> figure out how to set two types of listeners or create an options menu to start an edit list mode
        //to handle deleting the workouts
    }

    override  fun onStart() {
        super.onStart()

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) { Log.i(TAG, "No data received") }
                workouts.clear()

                var dbWorkout : Workout? = null

                for (data in dataSnapshot.children) {
                    try {

                        val dataMap : Map<String, Object> = data.value as Map<String, Object>
                        dbWorkout = Workout(dataMap)

                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    } finally {
                        workouts.add(dbWorkout!!)
                    }
                }

                val newAdapter : WorkoutListAdapter = WorkoutListAdapter(this@WorkoutActivity)
                newAdapter.addAll(workouts)
                workoutListAdapter = newAdapter
                listViewWorkouts.adapter = newAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, error.toString())
            }
        })

        buttonAddWorkout.setOnClickListener {
            addWorkout()
        }

        listViewWorkouts.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            //TODO -> (if we have time) add functionality to first show all of the exercises in the workout,
            //and if the user clicks the same workout again in the list then launch the activity.

            //get the selected workout
            val workout = workoutListAdapter.getItem(i)

            //create an intent and package it up
            val intent = Intent(applicationContext, WorkoutExercisesActivity::class.java)//TODO: NEED TO FINISH THIS ACTIVITY. IT SHOULD LIST THE WORKOUT_NAME, EXERCISES, REPS, WEIGHTS, SETS,
            // AND THEN GIVE A WAY FOR THE USER TO FILL IN WHAT THEY ACCOMPLISHED AND THEN SAVE IT TO THE DATABASE. A HISTORY OF OLD WORKOUTS SHOULD BE ADDED TO WELCOME.KT FOR VIEWING.
            intent.putExtra(WORKOUT_NAME, workout)
            intent.putExtra(USER_ID, uid)
            intent.putExtra(WORKOUT_TYPE, workoutType)
            startActivity(intent)
        }
    }

    private fun addWorkout() {
        val name = editTextName.text.toString()

        if (!TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Adding a custom workout...", Toast.LENGTH_LONG).show()

            val exerciseListIntent = Intent(this, AddWorkoutActivity::class.java)
            exerciseListIntent.putExtra(WORKOUT_NAME, name)
            exerciseListIntent.putExtra(WORKOUT_TYPE, workoutType)
            exerciseListIntent.putExtra(USER_ID, uid)

            editTextName.setText("")
            startActivityForResult(exerciseListIntent, ADD_WORKOUT_REQUEST)

            //The workout is actually added in the onActivityResult function
        }
        else {
            Toast.makeText(this, "Please enter a workout name", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == ADD_WORKOUT_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                //Get workout from intent
                val workout = data?.getParcelableExtra<Workout>(WORKOUT_NAME)!!
                //workout.writeToDatabase(mDatabase!!)                                                              //Am now adding to database in AddWorkoutActivity.kt
                workoutListAdapter.add(workout)


                Log.i(TAG, "Workout received and added in onActivityResult")
            } else {
                Log.i(TAG, "Workout not received, request code not RESULT_OK or data is null")
                Toast.makeText(this,
                    "Were sorry something went wrong. Please try to add your workout again!", Toast.LENGTH_LONG)
            }
        }
    }

    //TODO: Implement this
    private fun deleteWorkout(workout: Workout) {
        Toast.makeText(this, "Deleting a custom workout...", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val TAG = "Mine-WorkoutActivity"
        const val WORKOUT_NAME = "WORKOUT_NAME"
        const val WORKOUT_ID = "WORKOUT_ID"
        const val WORKOUT_EXERCISES = "WORKOUT_EXERCISES"
        const val ADD_WORKOUT_REQUEST = 0
        val USER_ID = "USER_ID"
        val WORKOUT_TYPE = "WORKOUT_TYPE"
        val BULK_UP = "BULK_UP"
        val WEIGHT_LOSS = "WEIGHT_LOSS"
        val ENDURANCE = "ENDURANCE"
        val FLEXIBILITY = "FLEXIBILITY"
    }

}
