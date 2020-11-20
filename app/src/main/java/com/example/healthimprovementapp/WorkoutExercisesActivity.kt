package com.example.healthimprovementapp.com.example.healthimprovementapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthimprovementapp.ExerciseListAdaptor
import com.example.healthimprovementapp.R
import com.google.firebase.database.FirebaseDatabase

class WorkoutExercisesActivity : AppCompatActivity() {

    private lateinit var mListView : ListView
    private lateinit var mListAdapter : ExerciseListAdaptor
    private lateinit var mSubmitButton : Button
    private lateinit var mWorkout : Workout

 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_exercise)

        //Set mListView and add mSubmitButton as a footer to the list
        mListView = findViewById<ListView>(R.id.exerciseList)
        mListView.setFooterDividersEnabled(true)
        mSubmitButton = findViewById(R.id.submitButton)
        mListView.addFooterView(mSubmitButton)

        //Get workout from the intent
        val workoutName = intent.getStringExtra(WORKOUT_NAME)
        val workoutId = intent.getStringExtra(WORKOUT_ID)
        val exerciseList = intent.getSerializableExtra(WORKOUT_EXERCISES)//TODO change to array list when passed as a whole (This code should work. OS)
        mWorkout = Workout(workoutName!!, workoutId!!, exerciseList as ArrayList<Exercise>)//TODO change to proper arrayList (This code should work. OS)

        //Setup List Adapter
        mListAdapter = ExerciseListAdaptor(this, R.layout.exercise_list, mWorkout)
        mListView.adapter = mListAdapter

    }

    companion object {
        const val TAG = "HealthImprovementApp Tag"
        const val WORKOUT_NAME = "WORKOUT_NAME"
        const val WORKOUT_ID = "WORKOUT_ID"
        const val WORKOUT_EXERCISES = "WORKOUT_EXERCISES"
        val USER_ID = "USER_ID"
    }
}