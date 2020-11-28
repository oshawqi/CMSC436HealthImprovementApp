package com.example.healthimprovementapp

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
import com.google.firebase.database.*
import java.util.*

class WorkoutActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var buttonAddWorkout: Button
    internal lateinit var listViewWorkouts: ListView
    internal lateinit var workouts: ArrayList<Workout>
    private lateinit var databaseWorkouts: DatabaseReference
    private lateinit var workout: Workout

    private var uid: String? = null
    private var workoutType : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        //Access the workouts node in the database
        databaseWorkouts = FirebaseDatabase.getInstance().getReference("workouts")

        editTextName = findViewById<View>(R.id.customWorkoutName) as EditText
        buttonAddWorkout = findViewById<View>(R.id.addCustomWorkoutButton) as Button
        listViewWorkouts = findViewById<View>(R.id.listViewWorkouts) as ListView

        workouts = ArrayList()

        //initialize UID and Workout Type here based on intent provided!!!!!!!
        uid = intent.getStringExtra(USER_ID)
        workoutType = intent.getStringExtra(WORKOUT_TYPE)

        buttonAddWorkout.setOnClickListener {
            addWorkout()
        }

        listViewWorkouts.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->

            //get the selected workout
            val workout = workouts[i]

            //create an intent and package it up
            val intent = Intent(applicationContext, WorkoutExercisesActivity::class.java)//TODO: NEED TO FINISH THIS ACTIVITY. IT SHOULD LIST THE WORKOUT_NAME, EXERCISES, REPS, WEIGHTS, SETS,
            // AND THEN GIVE A WAY FOR THE USER TO FILL IN WHAT THEY ACCOMPLISHED AND THEN SAVE IT TO THE DATABASE. A HISTORY OF OLD WORKOUTS SHOULD BE ADDED TO WELCOME.KT FOR VIEWING.
            intent.putExtra(WORKOUT_NAME, workout)
            startActivity(intent)
        }

        listViewWorkouts.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            val workout = workouts[i]
            deleteWorkout(workout)
            true
        }
    }

    private fun addWorkout() {
        Toast.makeText(this, "Adding a custom workout...", Toast.LENGTH_LONG).show()
        val name = editTextName.text.toString()

        if (!TextUtils.isEmpty(name)) {
            val id = databaseWorkouts.push().key

            val intent = Intent(this, AddWorkoutActivity::class.java)
            intent.putExtra(WORKOUT_ID, id)
            intent.putExtra(WORKOUT_NAME, name)
            startActivityForResult(intent, REQUEST_CODE)

            val exerciseListIntent = Intent(this, AddWorkoutActivity::class.java)
            exerciseListIntent.putExtra(WORKOUT_ID, id)
            exerciseListIntent.putExtra(WORKOUT_NAME, name)
            startActivityForResult(exerciseListIntent, REQUEST_CODE)

            databaseWorkouts.child(uid!!).child(id!!).setValue(workout)

            editTextName.setText("")

            Toast.makeText(this, "Workout added", Toast.LENGTH_LONG).show()

        }
        else {
            Toast.makeText(this, "Please enter a workout name", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //Get workout from intent
                workout = data?.getParcelableExtra<Workout>(WORKOUT_NAME)!!
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
                workouts.clear()

                var workoutFromDatabase: Workout? = null
                for (postSnapshot in dataSnapshot.child(uid!!).children) {
                    try {
                        workoutFromDatabase = postSnapshot.getValue(Workout::class.java)
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    } finally {
                        workouts.add(workoutFromDatabase!!)
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
        const val TAG = "HealthImprovementApp Tag"
        const val WORKOUT_NAME = "WORKOUT_NAME"
        const val WORKOUT_ID = "WORKOUT_ID"
        const val WORKOUT_EXERCISES = "WORKOUT_EXERCISES"
        const val REQUEST_CODE = 2
        val USER_ID = "USER_ID"
        val WORKOUT_TYPE = "WORKOUT_TYPE"
        val BULK_UP = "BULK_UP"
        val WEIGHT_LOSS = "WEIGHT_LOSS"
        val ENDURANCE = "ENDURANCE"
        val FLEXIBILITY = "FLEXIBILITY"
    }

}
