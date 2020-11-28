package com.example.healthimprovementapp

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

    private var uid: String? = null //TODO (nothing) but I changed this to a nullable variable, it can definitely be changed back if needed
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
        //TODO - possibly handle the event that the intent does not include one of these fields or is null
        uid = intent.getStringExtra(USER_ID)
        workoutType = intent.getStringExtra(WORKOUT_TYPE)

        buttonAddWorkout.setOnClickListener {
            addWorkout()
        }

        listViewWorkouts.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->

            //get the selected workout
            val workout = workouts[i]

            //create an intent and package it up
            val intent = Intent(applicationContext, WorkoutExercisesActivity::class.java)//TODO: NEED TO FINISH THIS ACTIVITY
            intent.putExtra(WORKOUT_ID, workout.workoutId)
            intent.putExtra(WORKOUT_NAME, workout.workoutName)
            intent.putExtra(WORKOUT_EXERCISES, workout.workoutExercises[0].toString())//TODO: FIGURE OUT HOW TO PASS THE ARRAYLIST
            startActivity(intent)
        }

        listViewWorkouts.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            val workout = workouts[i]
            deleteWorkout(workout)
            true


        }


    }

    //TODO: Finish implementing this - ensure a duplicate name is not entered. This func. should call a new activity.
    private fun addWorkout() {
        Toast.makeText(this, "Adding a custom workout...", Toast.LENGTH_LONG).show()
        val name = editTextName.text.toString()

        if (!TextUtils.isEmpty(name)) {
            val id = databaseWorkouts.push().key

            //TODO -> use startActivityForResult to start an add exercises activity to populate the exercise list
            //need to send it the UID and workout name so that they can be passed back to the onActivityResult method
            val intent = Intent(this, AddWorkoutActivity::class.java)
            intent.putExtra(WORKOUT_ID, id)
            intent.putExtra(WORKOUT_NAME, name)
            startActivityForResult(intent, REQUEST_CODE)

            val exerciseListIntent = Intent(this, ActivityCreateExerciseList::class.java)
            exerciseListIntent.putExtra(WORKOUT_ID, id)
            exerciseListIntent.putExtra(WORKOUT_NAME, name)
            startActivity(exerciseListIntent)

            //val workout = Workout(id!!, name, emptyList())

            if (id != null) {
                databaseWorkouts.child(uid!!).child(id).setValue(workout)
            }

            editTextName.setText("")

            Toast.makeText(this, "Workout added", Toast.LENGTH_LONG).show()

            //TODO -> add workout to list

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

        databaseWorkouts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                workouts.clear()

                var workout: Workout? = null
                for (postSnapshot in dataSnapshot.child(uid!!).children) {
                    try {
                        workout = postSnapshot.getValue(Workout::class.java)
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    } finally {
                        workouts.add(workout!!)
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








    //TODO: These need to be changed to fit this project
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