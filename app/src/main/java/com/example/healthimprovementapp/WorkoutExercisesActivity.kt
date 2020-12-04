package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthimprovementapp.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WorkoutExercisesActivity : AppCompatActivity() {

    private lateinit var mWorkoutNameView : TextView
    private lateinit var mListView : ListView
    private lateinit var mListAdapter : ExerciseListAdapter
    private lateinit var mSubmitButton : View
    private lateinit var mWorkout : Workout
    private lateinit var mWorkoutType : String
    private lateinit var uid : String
    private lateinit var mDatabase : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_exercise)

        //Get workout from the intent
        mWorkout = intent.getParcelableExtra<Workout>(WORKOUT_NAME)
        uid = intent.getStringExtra(USER_ID)
        mWorkoutType = intent.getStringExtra(WORKOUT_TYPE)
        if (mWorkout == null) {
            Log.i(TAG, "Intent Data not received properly... finishing")
            finish()
        }

        //Init database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("user-history").child(uid).child(mWorkoutType)

        //Set workout name on UI
        mWorkoutNameView = findViewById(R.id.workoutName)
        mWorkoutNameView.text = mWorkout.workoutName

        //Set mListView and add mSubmitButton as a footer to the list
        mListView = findViewById<ListView>(R.id.exerciseList)
        mListView.setFooterDividersEnabled(true)
        mSubmitButton =
            (applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.submit_workout_footer,
                null,
                false
            )
        mListView.addFooterView(mSubmitButton)

        //Setup List Adapter
        mListAdapter = ExerciseListAdapter(this)
        mListView.adapter = mListAdapter
        addAllExercises(mWorkout)
    }

    override fun onStart() {
        super.onStart()

        mSubmitButton.setOnClickListener {
            submitWorkout()
        }

        mListView.setOnItemClickListener { adapterView, view, i, l ->
            //TODO -> add a fragment that allows you to enter a new set reps and weight that also edits
            // them in the list adapter and for the workout in the database
            // we should probably have two options to replace the normal values in the workout or to make it an
            // entirely new workout with a new id.
        }

    }

    //Adds all exercises into the list view
    private fun addAllExercises(workout : Workout) {
        mListAdapter.addAll(workout.workoutExercises)
    }

    private fun submitWorkout() {
        mDatabase.child(mWorkout.workoutId!!).setValue(mWorkout)
        val intent = Intent(this, Welcome::class.java)
        intent.putExtra(USER_ID, uid)
        startActivity(intent)
    }

    companion object {
        const val TAG = "Mine-WorkoutExercisesActivity:"
        const val WORKOUT_NAME = "WORKOUT_NAME"
        val WORKOUT_TYPE = "WORKOUT_TYPE"
        const val ADD_WORKOUT_REQUEST = 0
        const val USER_ID = "USER_ID"
    }
}