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
    private lateinit var databaseWorkouts: DatabaseReference

    private lateinit var uid: String
    private lateinit var workoutType : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        //Initializes UID and Workout Type here based on intent provided
        uid = intent.getStringExtra(USER_ID) as String
        workoutType = intent.getStringExtra(WORKOUT_TYPE) as String

        //Access the workout's node in the database
        databaseWorkouts = FirebaseDatabase.getInstance().getReference(workoutType)

        editTextName = findViewById<View>(R.id.customWorkoutName) as EditText
        buttonAddWorkout = findViewById<View>(R.id.addCustomWorkoutButton) as Button

        //setup list view and adapter
        listViewWorkouts = findViewById<View>(R.id.listViewWorkouts) as ListView
        workoutListAdapter = WorkoutListAdapter(this)
        listViewWorkouts.adapter = workoutListAdapter

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
            startActivity(intent)
        }

        //TODO -> figure out how to set two types of listeners or create an options menu to start an edit list mode
        //to handle deleting the workouts

//        listViewWorkouts.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
//            val workout = workouts[i]
//            //TODO: Make a dialog box pop up asking "Are you sure you want to delete this workout?" before actually deleting it
//            deleteWorkout(workout)
//            true
//        }
    }

    private fun addWorkout() {
        Toast.makeText(this, "Adding a custom workout...", Toast.LENGTH_LONG).show()
        val name = editTextName.text.toString()

        if (!TextUtils.isEmpty(name)) {
            val id = databaseWorkouts.push().key

            val exerciseListIntent = Intent(this, AddWorkoutActivity::class.java)
            exerciseListIntent.putExtra(WORKOUT_ID, id)
            exerciseListIntent.putExtra(WORKOUT_NAME, name)
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
                workoutListAdapter.add(workout)

                val workoutString = workoutListAdapter.count.toString()
                Log.i(TAG, "Workout received and added in onActivityResult: $workoutString")
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


    override  fun onStart() {
        super.onStart()

        databaseWorkouts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //TODO -> had to delete the workouts.clear() (changed to workoutListAdapter.clear()) so the workouts would show up in the list
                var workoutFromDatabase: Workout? = null
                for (postSnapshot in dataSnapshot.child(uid!!).children) {
                    try {
                        workoutFromDatabase = postSnapshot.getValue(Workout::class.java)
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    } finally {
                        workoutListAdapter.add(workoutFromDatabase!!)
                    }
                }

                //val workoutListAdapter = WorkoutListAdaptor(this@WorkoutActivity ,workouts) //Reworked the workout manager to implement all of the exercises as a string and added an add function
                //listViewWorkouts.adapter = workoutListAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
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
