package com.example.healthimprovementapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.healthimprovementapp.com.example.healthimprovementapp.ExerciseList
import com.example.healthimprovementapp.com.example.healthimprovementapp.Workout
import com.example.healthimprovementapp.com.example.healthimprovementapp.WorkoutExercisesActivity
import com.google.firebase.database.*
import java.lang.Exception
import java.util.*

class WorkoutActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var buttonAddWorkout: Button
    internal lateinit var listViewWorkouts: ListView

    internal lateinit var workouts: MutableList<Workout>

    private lateinit var databaseWorkouts: DatabaseReference

    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        //Access the workouts node in the database
        databaseWorkouts = FirebaseDatabase.getInstance().getReference("workouts")

        editTextName = findViewById<View>(R.id.customWorkoutName) as EditText
        buttonAddWorkout = findViewById<View>(R.id.addCustomWorkoutButton) as Button
        listViewWorkouts = findViewById<View>(R.id.listViewWorkouts) as ListView

        workouts = ArrayList()

        //TODO: Need to initialize UID here based on intent provided!!!!!!!
        //
        //CODE GOES HERE!!
        //
        //

        buttonAddWorkout.setOnClickListener {
            addWorkout()
        }

        listViewWorkouts.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->

            //get the selected workout
            val workout = workouts[i]

            //create an intent and package it up
            val intent = Intent(applicationContext, WorkoutExercisesActivity::class.java)            //TODO: NEED TO FINISH THIS ACTIVITY
            intent.putExtra(WORKOUT_ID, workout.workoutId)
            intent.putExtra(WORKOUT_NAME, workout.workoutName)
            intent.putExtra(WORKOUT_EXERCISES, workout.workoutExercises[0].toString())                          //TODO: FIGURE OUT HOW TO PASS THE ARRAYLIST
            startActivity(intent)
        }

        listViewWorkouts.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            val workout = workouts[i]
            deleteWorkout(workout)
            true


        }


    }

    //TODO: Finish implementing this
    private fun addWorkout() {
        Toast.makeText(this, "Adding a custom workout...", Toast.LENGTH_LONG).show()
        val name = editTextName.text.toString()

        if (!TextUtils.isEmpty(name)) {
            val id = databaseWorkouts.push().key

            val workout = Workout(id!!, name, emptyList())

            databaseWorkouts.child(uid).child(id).setValue(workout)

            editTextName.setText("")

            Toast.makeText(this, "Workout added", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this, "Please enter a workout name", Toast.LENGTH_LONG).show()
        }
    }

    //TODO: Implement this
    private fun deleteWorkout(workout: Workout) {
        Toast.makeText(this, "Deleting a custom workout...", Toast.LENGTH_LONG).show()
    }


    override  fun onStart() {
        super.onStart()

        databaseWorkouts.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                workouts.clear()

                var workout: Workout? = null
                for (postSnapshot in dataSnapshot.child(uid).children) {
                    try {
                        workout = postSnapshot.getValue(Workout::class.java)
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    } finally {
                        workouts.add(workout!!)
                    }
                }

                val workoutListAdapter = ExerciseList(this@WorkoutActivity,
                    workout!!.workoutExercises                                                       //TODO: Check this for functionality
                )
                listViewWorkouts.adapter = workoutListAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }








    //TODO: These need to be changed to fit this project
    companion object {
        const val TAG = "HealthImprovementApp Tag"
        const val WORKOUT_NAME = "com.example.tesla.myhomelibrary.authorname"
        const val WORKOUT_ID = "com.example.tesla.myhomelibrary.authorid"
        const val WORKOUT_EXERCISES = "com.example.tesla.myhomelibrary.userid"
    }



}